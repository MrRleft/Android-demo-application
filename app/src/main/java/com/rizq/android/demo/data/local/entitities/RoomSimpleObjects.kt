package com.rizq.android.demo.data.local.entitities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rizq.android.domain.core.TransformableToDomainObject
import com.rizq.android.domain.models.local.Currency
import com.rizq.android.domain.models.local.RatesLM
import com.rizq.android.domain.models.local.TransactionsLM

@Entity (tableName = "Rates")
data class RatesRoomModel(
    val from: String,
    val rate: String,
    val to: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
): TransformableToDomainObject {
    override fun toDomain() = RatesLM(Currency.valueOf(from), rate.toDouble(),  Currency.valueOf(to))
}

@Entity (tableName = "Transactions")
data class TransactionsRoomModel(
    val amount: String,
    val currency: String,
    @PrimaryKey
    val sku: String
): TransformableToDomainObject {
    override fun toDomain() = TransactionsLM(amount.toDouble(), Currency.valueOf(currency), sku)
}