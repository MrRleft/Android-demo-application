package com.rizq.android.demo.framework.core.server

import android.util.Log
import android.util.Log.*
import com.rizq.android.demo.BuildConfig.DEBUG
import com.rizq.android.domain.core.*
import com.rizq.android.domain.empty
import com.rizq.android.domain.models.core.ResponseWrapper
import retrofit2.Call

interface NetworkManager {

  suspend fun <T, R> safeRequest(callRequest: Call<T>, functionCall: suspend (Either.Right<ResponseWrapper<T>>) -> Either<Failure, R>): Either<Failure, R>

  suspend fun <T, R> safeRequestParseFailureError(callRequest: Call<T>,
                                                  functionCall: suspend (Either.Right<ResponseWrapper<T>>) -> Either<Failure, R>,
                                                  functionCallFailure: suspend (Failure) -> Either.Left<Failure>): Either<Failure, R>

  @Suppress("BlockingMethodInNonBlockingContext")
  class NetworkImplementation : NetworkManager {

    override suspend fun <T, R> safeRequest(callRequest: Call<T>, functionCall: suspend (Either.Right<ResponseWrapper<T>>) -> Either<Failure, R>): Either<Failure, R> =
      try {
        val request = callRequest.execute()
        ((if (request.isSuccessful) Either.Right(ResponseWrapper(request.body(), request.code().toString()))
        else {
          Either.Left(Failure.ErrorBody(request.code(), request.errorBody()?.string() ?: String.empty))
        }).flatMapToRight { rightResult -> functionCall.invoke(Either.Right(rightResult)) })
      } catch (ex: Exception) {
        ex.message?.let { Log.d("ERROR", it) }
        Either.Left(Failure.ConnectivityError)
      }

    override suspend fun <T, R> safeRequestParseFailureError(callRequest: Call<T>,
                                                             functionCall: suspend (Either.Right<ResponseWrapper<T>>) -> Either<Failure, R>,
                                                             functionCallFailure: suspend (Failure) -> Either.Left<Failure>): Either<Failure, R> =
      try {
        val request = callRequest.execute()
        ((if (request.isSuccessful) Either.Right(ResponseWrapper(request.body(), request.code().toString()))
        else {
          Either.Left(Failure.ErrorBody(request.code(), request.errorBody()?.string() ?: String.empty))
        }).flatMapToRight { rightResult -> functionCall.invoke(Either.Right(rightResult)) }).flatMapToLeft { leftResult ->
          functionCallFailure.invoke(leftResult)
        }
      } catch (ex: Exception) {
        Either.Left(Failure.ConnectivityError)
      }
  }
}