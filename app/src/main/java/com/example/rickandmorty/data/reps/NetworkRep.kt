package com.example.rickandmorty.data.reps

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmorty.EntityPagingSource
import com.example.rickandmorty.data.network.ApiInterface
import javax.inject.Inject


class NetworkRep@Inject constructor(
    private val network:ApiInterface
) {

    fun getFlof() = Pager(
    PagingConfig(pageSize = 20,
        initialLoadSize = 20,
        enablePlaceholders = false),
    pagingSourceFactory = { EntityPagingSource(network) }).flow
}