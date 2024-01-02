package com.rizq.android.demo.data.local.dao

import androidx.room.*
import com.rizq.android.domain.models.local.RatesLM
import com.rizq.android.domain.models.local.TransactionsLM

@Dao
interface BankDao {

    @Insert
    suspend fun insertRate(rateEntity: RatesLM)

    @Insert
    suspend fun insertTransaction(transactionEntity: TransactionsLM)

    @Delete
    suspend fun deleteRate(rateEntity: RatesLM)

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionsLM)

    @Update
    suspend fun updateRate(rateEntity: RatesLM)

    @Update
    suspend fun updateTransaction(transactionEntity: TransactionsLM)

    @Query("SELECT * FROM rates ORDER BY id ASC")
    fun getAllRates(): List<RatesLM>

    @Query("SELECT * FROM transactions ORDER BY sku ASC")
    fun getAllTransactions(): List<TransactionsLM>
}