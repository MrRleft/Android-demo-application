package com.rizq.android.demo.di

import androidx.room.Room
import com.rizq.android.data.datasources.*
import com.rizq.android.data.repositories.local.RoomRepository
import com.rizq.android.data.repositories.server.GNBRepository
import com.rizq.android.demo.data.local.BankDatabase
import com.rizq.android.demo.data.local.RoomLocalDataSourceImplementation
import com.rizq.android.demo.data.server.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

  @Provides
  @Singleton
  fun provideJsonServerParser() = JsonServerParser()

  @Provides
  @Singleton
  fun provideLocalDataSource(bankDatabase: BankDatabase,
                             jsonServerParser: JsonServerParser): LocalDataSource =
    RoomLocalDataSourceImplementation(bankDatabase, jsonServerParser)

  @Provides
  @Singleton
  fun provideRemoteDataSource(
    apiService: APIService,
    jsonServerParser: JsonServerParser,
  ): RemoteDataSource = RetrofitDataSourceImplementation(apiService, jsonServerParser)

  @Provides
  @Singleton
  fun provideGNBRepository(remoteDataSource: RemoteDataSource): GNBRepository = GNBRepository(remoteDataSource)

  @Provides
  @Singleton
  fun provideRoomRepository(localDataSource: LocalDataSource): RoomRepository = RoomRepository(localDataSource)
}