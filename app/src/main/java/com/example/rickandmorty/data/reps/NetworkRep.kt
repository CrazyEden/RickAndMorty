package com.example.rickandmorty.data.reps

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmorty.EntityPagingSource
import com.example.rickandmorty.data.network.ApiInterface
import javax.inject.Inject


class NetworkRep@Inject constructor(
    private val network:ApiInterface
) {

    fun getFlof(name:String?,
                status:String?,
                gender:String?) = Pager(
    PagingConfig(pageSize = 20,
        initialLoadSize = 20,
        enablePlaceholders = false),
    pagingSourceFactory = {
        EntityPagingSource(network,
            name = name,
            status = status,
            gender = gender)
    }).flow
}