package com.rizq.android.usecases.gnb

import com.rizq.android.data.repositories.GNBRepository
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.*
import com.rizq.android.domain.models.local.Currency.*
import kotlinx.coroutines.flow.Flow

class GetCertainTransactionUC(val gnbRepository: GNBRepository, val bankersRoundingConversionSUC: BankersRoundingConversionSUC) :
  UseCaseFlow<GetCertainTransactionUC.Params, GetCertainTransactionUC.ReturnParams>() {

  data class Params(val transaction: String)
  data class ReturnParams(val totalValue: Double, val operations: List<TransactionsLM>)

  override suspend fun execute(params: Params): Flow<Either<Failure, ReturnParams>> {
    return when (val res = gnbRepository.getCertainTransaction(params.transaction)) {
      is Either.Left -> res
      is Either.Right -> getRatesAndOperate(res.b)
    }.asFlow()
  }

  private suspend fun getRatesAndOperate(transactions: List<TransactionsLM>): Either<Failure, ReturnParams> {
    return when (val rates = gnbRepository.getRatesFull()) {
      is Either.Left -> rates
      is Either.Right -> calculateSumOfTransactions(transactions, rates.b)
    }
  }

  private fun calculateSumOfTransactions(transactions: List<TransactionsLM>, rates: List<RatesLM>): Either<Failure, ReturnParams> {
    var totalSum: Double = 0.0

    transactions.forEach { transaction ->
      totalSum += if (transaction.currency == EUR) transaction.amount
      else {
        val conversionFactor = rates.find { rate -> rate.from == transaction.currency && rate.to == EUR }?.rate
        if (conversionFactor == null) return Either.Left(Failure.MissingRateFailure)
        bankersRoundingConversionSUC.run(BankersRoundingConversionSUC.Params(transaction.amount, conversionFactor))
      }
    }

    return Either.Right(ReturnParams(totalSum, transactions))
  }
}