package com.example.rickandmorty.ui.locations

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.data.network.ApiInterface

class LocationPagingSource(private val network: ApiInterface):PagingSource<Int,Location>(){
    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        val pageId = params.key?:1
        try {
            val res = network.getAllLocations(pageId)
            val list = res.body()?.results?.toList()!!
            val pKey = if (pageId == 1) null else pageId.minus(1)
            val nKey = if (pageId == res.body()?.info?.pages) null else pageId.plus(1)
            return LoadResult.Page(
                data = list,
                prevKey =pKey,
                nextKey =nKey
            )


        }catch (e:Exception){
            println("LocationPagingSource: $e")
            return LoadResult.Error(e)
        }
    }

}