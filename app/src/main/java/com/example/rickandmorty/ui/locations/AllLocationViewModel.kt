package com.example.rickandmorty.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmorty.data.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllLocationViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
):ViewModel() {
    fun getFlow() = networkRepository.getLocationFlow().cachedIn(viewModelScope)
}