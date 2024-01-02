package com.rizq.android.domain.models.server

import com.rizq.android.domain.core.TransformableToDomainObject
import com.rizq.android.domain.models.local.*

data class RatesSM(
  val from: String,
  val rate: String,
  val to: String
): TransformableToDomainObject {
  override fun toDomain() = RatesLM(Currency.valueOf(from), rate.toDouble(),  Currency.valueOf(to))
}

data class TransactionsSM(
  val amount: String,
  val currency: String,
  val sku: String
): TransformableToDomainObject {
  override fun toDomain() = TransactionsLM(amount.toDouble(), Currency.valueOf(currency), sku)
}