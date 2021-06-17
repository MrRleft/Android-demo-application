package com.rizq.android.domain.core

sealed class Failure {
    object NetworkConnection : Failure()

    class ServerErrorCode(val code: Int) : Failure()
    class ServerException(val throwable: Throwable) : Failure()
    class JsonException(val body: String) : Failure()
    class PermissionNotGranted(val permission: String) : Failure()
    class FeatureNotEnable(val hardwareFeature: HardwareFeature) : Failure()

    abstract class FeatureFailure : Failure()

    class NullResult : FeatureFailure()
}