package com.rizq.android.demo.data.local

import com.rizq.android.data.datasources.LocalDataSource
import com.rizq.android.demo.data.local.entitities.RatesRoomModel
import com.rizq.android.demo.data.local.entitities.TransactionsRoomModel
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

    override suspend fun getTransactionsGNB(): Either<Failure, List<TransactionsLM>> = withContext(Dispatchers.IO)
    {
        val dataList = bankDatabase.bankDao().getAllTransactions()
        val localModelList = dataList.map { it.toDomain() }
        if (dataList.isNotEmpty()) {
            Either.Right(localModelList)
        } else {
            Either.Left(Failure.NullResult)
        }
    }

    override suspend fun getRatesGNB(): Either<Failure, List<RatesLM>>  = withContext(Dispatchers.IO)
    {
        val dataList = bankDatabase.bankDao().getAllRates()
        val localModelList = dataList.map { it.toDomain() }
        if (dataList.isNotEmpty()) {
            Either.Right(localModelList)
        } else {
            Either.Left(Failure.NullResult)
        }
    }

    override suspend fun populateDatabase(rates: String, transactions: String): Either<Failure, Unit> =
        withContext(Dispatchers.IO) {
            try {
                val ratesList = parseObjectToSM<RatesRoomModel>(rates)
                val transactionsList = parseObjectToSM<TransactionsRoomModel>(transactions)

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