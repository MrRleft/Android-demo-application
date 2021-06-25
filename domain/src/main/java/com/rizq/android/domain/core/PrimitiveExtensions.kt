package com.rizq.android.domain

import java.math.*

val String.Companion.empty
    get() = ""

val Long.Companion.default
    get() = 0L

val Int.Companion.default
    get() = 0

fun Double.bankersRounding(): Double = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()
