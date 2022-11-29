package com.example.rickandmorty.ui.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.data.network.ApiInterface

sealed class EpisodeUiModel{
    class Item(val episode:Episode): EpisodeUiModel()
    class Header(val season :String): EpisodeUiModel()
}

class EpisodePagingSource(
    private val networkRep: ApiInterface,
) :PagingSource<Int,EpisodeUiModel>() {
    override fun getRefreshKey(state: PagingState<Int, EpisodeUiModel>): Int? {
        val anchorn = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorn) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeUiModel> {
        val pageId = params.key ?: 1
        try {
            val res = networkRep.getAllEpisodes(pageId)
            val pKey = if (pageId == 1) null else pageId.minus(1)
            val nKey = if (pageId == res.body()?.info?.pages) null else pageId.plus(1)

            val episodes = res.body()!!.results


            return LoadResult.Page(
                data = episodes.map { EpisodeUiModel.Item(it) },
                prevKey = pKey,
                nextKey = nKey
            )

        }catch (e:Exception){
            println("EpisodePagingSource: $e")
            return LoadResult.Error(e)
        }



    }


}