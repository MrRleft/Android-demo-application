package com.rizq.android.demo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizq.android.demo.data.local.dao.BankDao
import com.rizq.android.demo.data.local.entitities.RatesRoomModel
import com.rizq.android.demo.data.local.entitities.TransactionsRoomModel

@Database(entities = [RatesRoomModel::class, TransactionsRoomModel::class], version = 1)
abstract class BankDatabase: RoomDatabase()  {
    abstract fun bankDao(): BankDao
}