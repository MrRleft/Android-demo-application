package com.rizq.android.demo.framework.di

import com.rizq.android.demo.BuildConfig
import com.rizq.android.demo.framework.core.server.APIService
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import java.security.cert.X509Certificate
import java.util.concurrent.*
import javax.inject.Singleton
import javax.net.ssl.*

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
      override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit
      override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
      override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    val builder = OkHttpClient.Builder()

    builder.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)

    builder.hostnameVerifier { _, _ -> true }
    builder.addNetworkInterceptor(interceptor)
    return builder.connectTimeout(30L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).build()
  }

  @Provides
  @Singleton
  fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient).build()

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)
}