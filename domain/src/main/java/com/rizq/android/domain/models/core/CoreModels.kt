package com.rizq.android.domain.models.core

data class ResponseWrapper<out T>(val value: T?, val responseCode: String)