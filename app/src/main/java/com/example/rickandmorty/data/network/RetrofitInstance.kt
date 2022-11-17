package com.example.rickandmorty.data.network

import com.example.rickandmorty.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.client(client)
            .build()
    }
    val api: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
    private val client by lazy {
        OkHttpClient.Builder()
            .readTimeout(10000, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }
}