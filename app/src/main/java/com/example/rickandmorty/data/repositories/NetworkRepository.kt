package com.example.rickandmorty.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmorty.data.network.ApiInterface
import com.example.rickandmorty.ui.characters.EntityPagingSource
import com.example.rickandmorty.ui.episodes.EpisodePagingSource
import com.example.rickandmorty.ui.locations.LocationPagingSource
import javax.inject.Inject


class NetworkRepository@Inject constructor(
    private val network:ApiInterface
) {

    fun getFlof(name:String?,
                status:String?,
                gender:String?,
                mFilter:Boolean = false) = Pager(
    PagingConfig(pageSize = 20,
        initialLoadSize = 20,
        enablePlaceholders = false),
    pagingSourceFactory = {
        EntityPagingSource(network,
            name = name,
            status = status,
            gender = gender,
            mFilter = mFilter)
    }).flow

    fun getEpisodesFlow() = Pager(
        PagingConfig(pageSize = 20,
            initialLoadSize = 20,
            enablePlaceholders = false),
        pagingSourceFactory = { EpisodePagingSource(network) }
    ).flow

    fun getLocationFlow() = Pager(
        PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { LocationPagingSource(network)}
    ).flow

    suspend fun getEpisode(id:Int) = network.getEpisodeById(id)

    suspend fun getEntityListByListIds(listOfId:String) = network.getMultipleCharacters(listOfId)
}