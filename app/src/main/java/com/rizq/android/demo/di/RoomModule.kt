package com.rizq.android.demo.di

import android.content.Context
import androidx.room.Room
import com.rizq.android.demo.data.local.BankDatabase
import com.rizq.android.demo.data.local.dao.BankDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): BankDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BankDatabase::class.java,
            "bank_database"
        ).build()
    }

    @Provides
    fun provideUserDao(appDatabase: BankDatabase): BankDao {
        return appDatabase.bankDao()
    }
}