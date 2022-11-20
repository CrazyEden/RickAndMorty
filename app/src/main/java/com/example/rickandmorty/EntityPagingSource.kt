package com.example.rickandmorty

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.network.ApiInterface
import javax.inject.Inject

class EntityPagingSource @Inject constructor(
    private val networkRep: ApiInterface
) :PagingSource<Int,Entity>() {
    override fun getRefreshKey(state: PagingState<Int, Entity>): Int? {
        return null
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Entity> {
        val pageId = params.key ?: 1
        try {
            val res = networkRep.getPage(pageId)

            val pKey = res.body()?.info?.prev?.split("=")?.get(1)?.toInt()
            val nKey = res.body()?.info?.next?.split("=")?.get(1)?.toInt()

                val entities = res.body()!!.results
                return LoadResult.Page(
                    data = entities,
                    prevKey = pKey,
                    nextKey = nKey
                )

        }catch (e:Exception){
            println(e)
            return LoadResult.Error(e)

        }



    }


}