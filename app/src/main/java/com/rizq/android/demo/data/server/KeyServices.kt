package com.rizq.android.demo.data.server

import retrofit2.Call
import retrofit2.http.*

interface APIService {

  @Headers("Content-Type: application/json")
  @GET("/transactions.json")
  fun getTransactions(): Call<String>

  @Headers("Content-Type: application/json")
  @GET("/rates.json")
  fun getRates(): Call<String>
}
