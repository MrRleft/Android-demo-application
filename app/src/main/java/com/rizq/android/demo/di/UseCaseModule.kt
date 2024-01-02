package com.rizq.android.demo.di

import com.rizq.android.data.repositories.server.GNBRepository
import com.rizq.android.usecases.gnb.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
  @Provides
  @Singleton
  fun providesBankersRoundingConversionSUC() = BankersRoundingConversionSUC()

  @Provides
  @Singleton
  fun provideGetAllTransactionsUC(gnbRepository: GNBRepository) = GetAllTransactionsUC(gnbRepository)

  @Provides
  @Singleton
  fun provideGetCertainTransactionSumUC(gnbRepository: GNBRepository, bankersRoundingConversionSUC: BankersRoundingConversionSUC) =
    GetCertainTransactionSumUC(gnbRepository, bankersRoundingConversionSUC)
}