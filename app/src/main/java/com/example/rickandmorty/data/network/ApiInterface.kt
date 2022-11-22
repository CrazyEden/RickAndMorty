package com.example.rickandmorty.data.network


import com.example.rickandmorty.data.model.ResponseTst
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("character")
    suspend fun getAllCharacters(
        @Query("page")page:Int,
        @Query("name")name:String? = null,
        @Query("status")status:String? = null,
        @Query("gender")gender:String? = null
    ): Response<ResponseTst>

}