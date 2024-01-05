package com.rizq.android.demo.data.local.entitities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rizq.android.domain.core.TransformableToDomainObject
import com.rizq.android.domain.models.local.Currency
import com.rizq.android.domain.models.local.RatesLM
import com.rizq.android.domain.models.local.TransactionsLM

@Entity(tableName = "Rates")
data class RatesRoomModel(
    @ColumnInfo(name = "from")
    val from: String,
    @ColumnInfo(name = "rate")
    val rate: String,
    @ColumnInfo(name = "to")
    val to: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0
): TransformableToDomainObject {
    override fun toDomain() = RatesLM(Currency.stringToCurrency(from),
        rate.toDouble(),
        Currency.stringToCurrency(to))
}

@Entity(tableName = "Transactions")
data class TransactionsRoomModel(
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "currency")
    val currency: String,
    @PrimaryKey
    @ColumnInfo(name = "sku")
    val sku: String
): TransformableToDomainObject {
    override fun toDomain() = TransactionsLM(
        amount.toDouble(),
        Currency.stringToCurrency(currency),
        sku)
}