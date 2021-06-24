package com.rizq.android.demo.framework.core.server

import retrofit2.Call
import retrofit2.http.*

interface APIService {

  @GET("/transactions.json")
  fun getTransactions(@Url url: String): Call<String>

  @GET("/rates.json")
  fun getRates(@Url url: String): Call<String>
}
