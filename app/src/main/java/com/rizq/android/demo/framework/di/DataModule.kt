package com.rizq.android.demo.framework.di

import com.rizq.android.data.datasources.*
import com.rizq.android.demo.framework.core.server.*
import com.rizq.android.demo.framework.datasources.*
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
  fun provideRetrofitDataSource(
    apiService: APIService,
    jsonServerParser: JsonServerParser,
  ): RemoteDataSource = RetrofitDataSourceImplementation(apiService, jsonServerParser)
}