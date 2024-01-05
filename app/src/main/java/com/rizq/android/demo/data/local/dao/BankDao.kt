package com.rizq.android.demo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rizq.android.demo.data.local.entitities.RatesRoomModel
import com.rizq.android.demo.data.local.entitities.TransactionsRoomModel

@Dao
interface BankDao {

    @Insert
    suspend fun insertRate(rateEntity: RatesRoomModel)

    @Insert
    suspend fun insertTransaction(transactionEntity: TransactionsRoomModel)

    @Delete
    suspend fun deleteRate(rateEntity: RatesRoomModel)

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionsRoomModel)

    @Update
    suspend fun updateRate(rateEntity: RatesRoomModel)

    @Update
    suspend fun updateTransaction(transactionEntity: TransactionsRoomModel)

    @Query("SELECT * FROM rates ORDER BY id ASC")
    fun getAllRates(): List<RatesRoomModel>

    @Query("SELECT * FROM transactions ORDER BY sku ASC")
    fun getAllTransactions(): List<TransactionsRoomModel>
}