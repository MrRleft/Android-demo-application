package com.rizq.android.data.repositories

import com.rizq.android.data.datasources.RemoteDataSource
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.*

class GNBRepository(val remoteDataSource: RemoteDataSource) {

  suspend fun getAllTransactions(): Either<Failure, List<TransactionsLM>> = remoteDataSource.getTransactionsGNB()

  suspend fun getCertainTransaction(sku: String): Either<Failure, List<TransactionsLM>> {
    return when (val res = remoteDataSource.getTransactionsGNB()) {
      is Either.Left -> res
      is Either.Right -> Either.Right(res.b.filter { it.sku == sku })
    }
  }

  suspend fun getRates(): Either<Failure, List<RatesLM>> = remoteDataSource.getRatesGNB()

  suspend fun getRatesFull(): Either<Failure, List<RatesLM>> {
    return when (val res = getRates()){
      is Either.Left -> res
      is Either.Right -> Either.Right(getMissingRates(res.b))
    }
  }

  private fun getMissingRates(fullListRates: List<RatesLM>): List<RatesLM> {
    val ratesReady = fullListRates.filter { rate -> rate.to == Currency.EUR }.distinct().toMutableList()
    val pendingRates = mutableListOf<Currency>()
    fullListRates.filter { rate -> rate.to != Currency.EUR }.forEach { pendingRates.add(it.from) }
    val distinctPendingCurrencies = pendingRates.distinct().toMutableList()
    var laps = 0
    while(ratesReady.size < Currency.values().size && laps < Currency.values().size && !distinctPendingCurrencies.isEmpty()){
      distinctPendingCurrencies.forEach { currencyWithoutConversion ->
        tryToFindCFForOneCurrency(fullListRates, currencyWithoutConversion, ratesReady, distinctPendingCurrencies)
      }
      laps++
    }
    return ratesReady
  }

  private fun tryToFindCFForOneCurrency(fullListRates: List<RatesLM>,
                                        currencyWithoutConversion: Currency,
                                        ratesReady: MutableList<RatesLM>,
                                        distinctPendingCurrencies: List<Currency>) {
    val availableInfoForOurRate = fullListRates.filter { rate -> rate.from == currencyWithoutConversion }
    availableInfoForOurRate.find { availableInfo ->
      val inmediateConversionFactor = ratesReady.find { it.from == availableInfo.to }
      if (inmediateConversionFactor != null) {
        val newConversionFactor = availableInfo.rate * inmediateConversionFactor.rate
        ratesReady.add(RatesLM(currencyWithoutConversion, newConversionFactor, Currency.EUR))
        distinctPendingCurrencies.dropWhile { it == currencyWithoutConversion }
        true
      }
      else false
    }
  }
}