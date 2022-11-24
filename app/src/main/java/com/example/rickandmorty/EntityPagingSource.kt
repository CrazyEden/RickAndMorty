package com.example.rickandmorty

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.network.ApiInterface

sealed class CharacterUiModel{
    class Item(val entity: Entity): CharacterUiModel()
    class Header(val char :String): CharacterUiModel()
}

class EntityPagingSource(
    private val networkRep: ApiInterface,
    private val name:String?,
    private val status:String?,
    private val gender:String?,
    private val mFilter:Boolean = false
) :PagingSource<Int,CharacterUiModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterUiModel>): Int? {
        return null
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterUiModel> {
        val pageId = params.key ?: 1
        try {
            val res = networkRep.getAllCharacters(
                page=pageId,
                name=this.name,
                status=this.status,
                gender=this.gender
            )
            val pKey = if (pageId == 1) null else pageId.minus(1)
            val nKey = if (pageId == res.body()?.info?.pages) null else pageId.plus(1)

            val entities = res.body()!!.results
            return if (mFilter){
                val mEntities = mutableListOf<Entity>()
                entities.forEach { if (it.name!!.startsWith(name!!)) mEntities.add(it) }
                LoadResult.Page(
                    data = mEntities.map { CharacterUiModel.Item(it) },
                    prevKey = pKey,
                    nextKey = nKey
                )
            }else
                LoadResult.Page(
                    data = entities.map { CharacterUiModel.Item(it) },
                    prevKey = pKey,
                    nextKey = nKey
                )
        }catch (e:Exception){
            println("EntityPagingSource: $e")
            return LoadResult.Error(e)
        }



    }


}