package com.rizq.android.demo.framework.di

import android.content.Context
import com.rizq.android.demo.ui.converters.ConvertObjectWithImageUrlToUI
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ConvertersModule {

  @Provides
  @Singleton
  fun providesConvertCommunicationsToUI(@ApplicationContext context: Context): ConvertObjectWithImageUrlToUI = ConvertObjectWithImageUrlToUI(context)
}