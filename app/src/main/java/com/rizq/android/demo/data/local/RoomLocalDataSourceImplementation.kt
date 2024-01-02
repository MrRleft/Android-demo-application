package com.rizq.android.demo.data.local

import com.rizq.android.data.datasources.LocalDataSource
import com.rizq.android.demo.data.server.APIService
import com.rizq.android.demo.data.server.JsonServerParser
import com.rizq.android.domain.core.Either
import com.rizq.android.domain.core.Failure
import com.rizq.android.domain.models.core.ResponseWrapper
import com.rizq.android.domain.models.local.RatesLM
import com.rizq.android.domain.models.local.TransactionsLM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class RoomLocalDataSourceImplementation @Inject constructor(
    private val bankDatabase: BankDatabase,
    private val jsonServerParser: JsonServerParser) : LocalDataSource {

    private inline fun <reified T> parseObjectToSM(data: String, certainObject: String? = null): List<T> {
        val json = if (certainObject != null) JSONObject(data).getString(certainObject) else data
        return jsonServerParser.parseArrayResponse<T>(json)
    }

    override suspend fun getTransactionsGNB(): Either<Failure, List<TransactionsLM>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRatesGNB(): Either<Failure, List<RatesLM>> {
        TODO("Not yet implemented")
    }

    override suspend fun populateDatabase(rates: String, transactions: String): Either<Failure, Unit> =
        withContext(Dispatchers.IO) {
            try {
                val ratesList = parseObjectToSM<RatesLM>(rates)
                val transactionsList = parseObjectToSM<TransactionsLM>(transactions)

                ratesList.forEach {
                    bankDatabase.bankDao().insertRate(it)
                }

                transactionsList.forEach {
                    bankDatabase.bankDao().insertTransaction(it)
                }
                Either.Right(Unit)
            } catch (ex: Exception) {
                Either.Left(Failure.NullResult)
            }
    }
}