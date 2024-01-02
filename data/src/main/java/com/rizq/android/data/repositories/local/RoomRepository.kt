package com.rizq.android.data.repositories.local

import com.rizq.android.data.datasources.LocalDataSource
import com.rizq.android.domain.core.Either
import com.rizq.android.domain.core.Failure
import com.rizq.android.domain.models.local.Currency
import com.rizq.android.domain.models.local.RatesLM
import com.rizq.android.domain.models.local.TransactionsLM

class RoomRepository (private val roomDataSource: LocalDataSource) {

    suspend fun populateDatabase(rates: String, transactions: String) =
        roomDataSource.populateDatabase(rates, transactions)

    suspend fun getAllTransactions(): Either<Failure, List<TransactionsLM>> = roomDataSource.getTransactionsGNB()

    suspend fun getCertainTransaction(sku: String): Either<Failure, List<TransactionsLM>> {
        return when (val res = roomDataSource.getTransactionsGNB()) {
            is Either.Left -> res
            is Either.Right -> Either.Right(res.b.filter { it.sku == sku })
        }
    }

    suspend fun getRates(): Either<Failure, List<RatesLM>> = roomDataSource.getRatesGNB()

    suspend fun getRatesFull(): Either<Failure, List<RatesLM>> {
        return when (val res = getRates()){
            is Either.Left -> res
            is Either.Right -> Either.Right(res.b)
        }
    }
}