package com.rizq.android.demo.data.server

import com.google.gson.*
import com.rizq.android.data.datasources.RemoteDataSource
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.core.ResponseWrapper
import com.rizq.android.domain.models.local.*
import com.rizq.android.domain.models.server.*
import kotlinx.coroutines.*
import org.json.JSONObject
import javax.inject.Inject

class RetrofitDataSourceImplementation @Inject constructor(private val apiService: APIService, private val jsonServerParser: JsonServerParser) :
  RemoteDataSource, NetworkManager by NetworkManager.NetworkImplementation() {

  private fun parseFailureToFailureWithErrorCode(it: Failure): Either.Left<Failure> = try {
    if (it is Failure.ErrorBody) Either.Left(Failure.ErrorWithParsedBody(Gson().fromJson(it.body, ErrorBodyParsed::class.java)))
    else Either.Left(it)
  } catch (ex: JsonParseException) {
    Either.Left(it)
  }

  private inline fun <reified T> parseObjectToSM(result: Either.Right<ResponseWrapper<String>>, certainObject: String? = null) =
    if (result.b.value != null) {
      val json = if (certainObject != null) JSONObject(result.b.value!!).getString(certainObject) else result.b.value!!
      when (val serverModel = jsonServerParser.genericParseDecodedString<T>(json)) {
        is Either.Right<*> -> Either.Right(serverModel.b)
        else -> serverModel
      }
    } else Either.Left(Failure.NullResult)

  private inline fun <reified T> returnArrayResults(domainModel: List<T>) = if (domainModel.isEmpty()) Either.Left(Failure.NullResult)
  else Either.Right(domainModel)

  private inline fun <reified T> parseArrayToSM(result: Either.Right<ResponseWrapper<String>>) = if (result.b.value != null) {
    try {
      jsonServerParser.parseArrayResponse<T>(result.b.value!!)
    } catch (ex: Exception) {
      null
    }
  } else null

  override suspend fun getTransactionsGNB(): Either<Failure, List<TransactionsLM>> = withContext(Dispatchers.IO) {
    safeRequest(apiService.getTransactions()) { result ->
      val domainModel: MutableList<TransactionsLM> = mutableListOf()
      parseArrayToSM<TransactionsSM>(result)?.forEach { domainModel += it.toDomain() }
      returnArrayResults(domainModel)
    }
  }

  override suspend fun getRatesGNB(): Either<Failure, List<RatesLM>> = withContext(Dispatchers.IO) {
    safeRequest(apiService.getRates()) { result ->
      val domainModel: MutableList<RatesLM> = mutableListOf()
      parseArrayToSM<RatesSM>(result)?.forEach { domainModel += it.toDomain() }
      returnArrayResults(domainModel)
    }
  }
}

