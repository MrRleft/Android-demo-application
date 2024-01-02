package com.rizq.android.usecases.gnb

import com.rizq.android.data.repositories.server.GNBRepository
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.*
import com.rizq.android.domain.models.local.Currency.*
import kotlinx.coroutines.flow.Flow

class GetCertainTransactionSumUC(private val gnbRepository: GNBRepository,
                                 private val bankersRoundingConversionSUC: BankersRoundingConversionSUC) :
  UseCaseFlow<GetCertainTransactionSumUC.Params, GetCertainTransactionSumUC.ReturnParams>() {

  data class Params(val transaction: List<TransactionsLM>)
  data class ReturnParams(val totalValue: Double, val operations: List<TransactionsLM>)

  override suspend fun execute(params: Params): Flow<Either<Failure, ReturnParams>> {
    return getRatesAndOperate(params.transaction).asFlow()
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
            ?: return Either.Left(Failure.MissingRateFailure)
        bankersRoundingConversionSUC.run(BankersRoundingConversionSUC.Params(transaction.amount, conversionFactor))
      }
    }

    return Either.Right(ReturnParams(totalSum, transactions))
  }
}