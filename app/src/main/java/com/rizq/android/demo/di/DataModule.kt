package com.rizq.android.demo.di

import com.rizq.android.data.datasources.*
import com.rizq.android.data.repositories.server.GNBRepository
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
  fun provideLocalDataSource(): LocalDataSource = RoomLocalDataSourceImplementation()

  @Provides
  @Singleton
  fun provideRemoteDataSource(
    apiService: APIService,
    jsonServerParser: JsonServerParser,
  ): RemoteDataSource = RetrofitDataSourceImplementation(apiService, jsonServerParser)

  @Provides
  @Singleton
  fun provideGNBRepository(remoteDataSource: RemoteDataSource): GNBRepository = GNBRepository(remoteDataSource)
}