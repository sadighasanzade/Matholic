package com.example.matholic.network

import com.example.matholic.Utility.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val api : MatholicApi by lazy{
        retrofit.create(MatholicApi::class.java)
    }
}