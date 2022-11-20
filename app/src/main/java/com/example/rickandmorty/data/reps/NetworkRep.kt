package com.example.rickandmorty.data.reps

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmorty.EntityPagingSource
import com.example.rickandmorty.data.network.ApiInterface
import javax.inject.Inject


class NetworkRep@Inject constructor(
    private val network:ApiInterface
    //private val pagingSourceFactory:EntityPagingSource
) {
//    suspend fun loadPageById(pageId:Int = 1):Response<ResponseTst> = network.getPage(pageId)
//    fun getPagedPage(): Flow<PagingData<Entity>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {EntityPagingSource(network)}
//        ).flow
//    }
//    suspend fun getEntity(id:Int) = network.getOneEntity(id)
    fun getFlof() = Pager(
    PagingConfig(pageSize = 20,
        initialLoadSize = 20,
        enablePlaceholders = false),
    pagingSourceFactory = { EntityPagingSource(network) }).flow
}