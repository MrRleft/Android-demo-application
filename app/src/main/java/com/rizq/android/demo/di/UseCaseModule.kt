package com.rizq.android.demo.di

import com.rizq.android.data.repositories.local.RoomRepository
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
  fun provideGetAllTransactionsUC(gnbRepository: GNBRepository, roomRepository: RoomRepository) =
    GetAllTransactionsUC(gnbRepository, roomRepository)

  @Provides
  @Singleton
  fun providePopulateDatabaseUC(roomRepository: RoomRepository) =
    PopulateDatabaseUC(roomRepository)

  @Provides
  @Singleton
  fun provideGetCertainTransactionSumUC(gnbRepository: GNBRepository,
                                        roomRepository: RoomRepository,
                                        bankersRoundingConversionSUC: BankersRoundingConversionSUC) =
    GetCertainTransactionSumUC(gnbRepository, roomRepository, bankersRoundingConversionSUC)
}