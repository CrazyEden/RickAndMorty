package com.example.rickandmorty.data.network


import com.example.rickandmorty.data.model.ResponseTst
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("character")
    suspend fun getPage(@Query("page")page:Int): Response<ResponseTst>
}