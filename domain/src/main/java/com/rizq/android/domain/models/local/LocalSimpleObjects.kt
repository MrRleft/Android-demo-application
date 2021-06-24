package com.rizq.android.domain.models.local

data class RatesLM(
  val from: Currency,
  val rate: Double,
  val to: Currency
): LocalObjectInterface {
  fun equals(other: RatesLM): Boolean {
    return from == other.from && rate == other.rate && to == other.to
  }
}

data class TransactionsLM(
  val amount: Double,
  val currency: Currency,
  val sku: String
): LocalObjectInterface

enum class Currency{
  USD, AUD, EUR, CAD
}