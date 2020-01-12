package com.seher.debtproject.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ServerAPI {


    @GET("latest")
    fun getMoney(
        @Query("access_key") api_token: String
    ): Call<MoneyResponse>
}
