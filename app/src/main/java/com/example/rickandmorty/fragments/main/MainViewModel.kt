package com.example.rickandmorty.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.EntityPagingSource
import com.example.rickandmorty.data.model.Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pagingSourceFactory:EntityPagingSource
) : ViewModel() {

    val sFlof: StateFlow<PagingData<Entity>> = Pager(
        PagingConfig(pageSize = 20)
    ){
        pagingSourceFactory
    }.flow.stateIn(viewModelScope, SharingStarted.Lazily,PagingData.empty())


}