package com.example.rickandmorty.data.network


import com.example.rickandmorty.data.model.ResponseTst
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("character")
    suspend fun getFirst(): Response<ResponseTst>
}