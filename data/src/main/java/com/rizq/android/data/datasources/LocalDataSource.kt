package com.rizq.android.data.datasources

import com.rizq.android.domain.core.Either
import com.rizq.android.domain.core.Failure
import com.rizq.android.domain.models.local.RatesLM
import com.rizq.android.domain.models.local.TransactionsLM

interface LocalDataSource {
    suspend fun getTransactionsGNB(): Either<Failure, List<TransactionsLM>>
    suspend fun getRatesGNB(): Either<Failure, List<RatesLM>>
    suspend fun populateDatabase(rates: String, transactions: String): Either<Failure, Unit>
}