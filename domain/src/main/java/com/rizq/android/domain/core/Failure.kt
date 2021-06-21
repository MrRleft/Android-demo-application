package com.rizq.android.domain.core

sealed class Failure {
    object NetworkConnection : Failure()
    data class JsonException(val body: String?) : Failure()
    data class ErrorBody(val requestCode: Int, val body: String?) : Failure()
    data class ErrorWithParsedBody(val errorData: ErrorBodyParsed) : Failure()
    data class ServerError(val errorCode: String) : Failure()
    data class ServerException(val serverException: Throwable) : Failure()
    class ServerErrorCode(val code: Int) : Failure()
    class PermissionNotGranted(val permission: String) : Failure()
    object ConnectivityError: Failure()

    abstract class FeatureFailure : Failure()

    class NullResult : FeatureFailure()
}

data class ErrorBodyParsed(val status: Int, val errorCode: String?, val errorDescription: String)
