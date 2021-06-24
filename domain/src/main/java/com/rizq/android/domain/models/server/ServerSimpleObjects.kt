package com.rizq.android.domain.models.server

import com.rizq.android.domain.models.local.*

interface ServerObjectsInterface {
  abstract fun toDomain(): LocalObjectInterface
}

data class RatesSM(
  val from: String,
  val rate: String,
  val to: String
): ServerObjectsInterface {
  override fun toDomain() = RatesLM(Currency.valueOf(from), rate.toDouble(),  Currency.valueOf(to))
}

data class TransactionsSM(
  val amount: String,
  val currency: String,
  val sku: String
): ServerObjectsInterface {
  override fun toDomain() = TransactionsLM(amount.toDouble(), Currency.valueOf(currency), sku)
}