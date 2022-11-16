package com.example.rickandmorty

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.network.ApiInterface
import retrofit2.HttpException
import javax.inject.Inject

class EntityPagingSource@Inject constructor(
    private val networkRep: ApiInterface
) :PagingSource<Int,Entity>() {
    override fun getRefreshKey(state: PagingState<Int, Entity>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Entity> {
        val pageId = params.key ?: 1
        val res = networkRep.getPage(pageId)
//        println("PREV AND NEXT${res.body()?.info?.prev?.last()?.toInt()}")
//        println("PREV AND NEXT${res.body()?.info?.next?.last()?.toInt()}")
//        val url = res.body()?.info?.next

        if (res.isSuccessful) {
            val entitys = res.body()!!.results
            return LoadResult.Page(
                data = entitys,
                prevKey = 2,
                nextKey = 2
            )
        }else return LoadResult.Error(HttpException(res))

    }


}