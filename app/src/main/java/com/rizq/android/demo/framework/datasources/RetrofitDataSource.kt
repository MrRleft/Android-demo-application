package com.rizq.android.demo.framework.datasources

import android.annotation.SuppressLint
import android.os.Build
import android.util.Base64
import com.google.gson.*
import com.rizq.android.data.RemoteDataInterface
import com.rizq.android.demo.framework.core.server.*
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.core.ResponseWrapper
import kotlinx.coroutines.*
import org.json.JSONObject
import java.security.*
import javax.crypto.Cipher
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(private val apiService: APIService, private val jsonServerParser: JsonServerParser) :
  RemoteDataInterface, NetworkManager by NetworkManager.NetworkImplementation() {

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
    } else Either.Left(Failure.NullResult())

  private inline fun <reified T> returnArrayResults(domainModel: List<T>) = if (domainModel.isEmpty()) Either.Left(Failure.NullResult())
  else Either.Right(domainModel)

  private inline fun <reified T> parseArrayToSM(result: Either.Right<ResponseWrapper<String>>) = if (result.b.value != null) {
    try {
      jsonServerParser.parseArrayResponse<T>(JSONObject(result.b.value!!).getString("data"))
    } catch (ex: Exception) {
      null
    }
  } else null
}

