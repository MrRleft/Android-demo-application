package com.rizq.android.demo.framework.di

import android.content.Context
import com.rizq.android.data.*
import com.rizq.android.demo.framework.core.server.*
import com.rizq.android.demo.framework.datasources.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
  fun provideLocalDataSource(): LocalDataInteface = RoomLocalDataSource()

  @Provides
  @Singleton
  fun provideRetrofitDataSource(
    apiService: APIService,
    jsonServerParser: JsonServerParser,
  ): RemoteDataInterface = RetrofitDataSource(apiService, jsonServerParser)
}