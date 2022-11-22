package com.example.rickandmorty

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.network.ApiInterface

class EntityPagingSource(
    private val networkRep: ApiInterface,
    private val name:String?,
    private val status:String?,
    private val gender:String?,
) :PagingSource<Int,Entity>() {
    override fun getRefreshKey(state: PagingState<Int, Entity>): Int? {
        return null
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Entity> {
        val pageId = params.key ?: 1
        try {
            val res = networkRep.getAllCharacters(
                page=pageId,
                name=this.name,
                status=this.status,
                gender=this.gender
            )

//            val pKey = res.body()?.info?.prev?.split("=")?.get(1)?.toInt()
//            val nKey = res.body()?.info?.next?.split("=")?.get(1)?.toInt()

            val pKey = if (pageId == 1) null else pageId.minus(1)
            val nKey = if (pageId == res.body()?.info?.pages) null else pageId.plus(1)

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