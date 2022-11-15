package com.example.rickandmorty.data.reps

import com.example.rickandmorty.data.model.ResponseTst
import com.example.rickandmorty.data.network.ApiInterface
import retrofit2.Response
import javax.inject.Inject


class NetworkRep@Inject constructor(
    private val network:ApiInterface
) {
    suspend fun loadFirstStack():Response<ResponseTst> = network.getFirst()
}