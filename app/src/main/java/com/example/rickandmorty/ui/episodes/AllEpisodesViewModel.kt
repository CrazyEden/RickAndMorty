package com.example.rickandmorty.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.rickandmorty.data.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AllEpisodesViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
):ViewModel() {
    fun load() = networkRepository.getEpisodesFlow().cachedIn(viewModelScope).map {
        it.insertSeparators { model1: EpisodeUiModel?, model2: EpisodeUiModel? ->
            if (model1 == null && model2 != null)
                return@insertSeparators EpisodeUiModel.Header("Season 1")
            if (model2 == null) return@insertSeparators null
            if (model1 is EpisodeUiModel.Header || model2 is EpisodeUiModel.Header)
                return@insertSeparators null
            val two = (model2 as EpisodeUiModel.Item).episode.codeOfEpisode?.dropLast(3)?.drop(1)
            val one = (model1 as EpisodeUiModel.Item).episode.codeOfEpisode?.dropLast(3)?.drop(1)
            if (one == two) return@insertSeparators null
            val str = "Season ${two?.toInt()}"
            return@insertSeparators EpisodeUiModel.Header(str)

        }
    }
}