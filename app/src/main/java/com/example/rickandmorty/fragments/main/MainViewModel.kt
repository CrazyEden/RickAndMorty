package com.example.rickandmorty.fragments.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.EntityPagingSource
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.reps.NetworkRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRep: NetworkRep,
    private val pagingSourceFactory:EntityPagingSource
) : ViewModel() {
    private val _liveData = MutableLiveData<List<Entity>>()
    val liveData = _liveData
    val sFlof: StateFlow<PagingData<Entity>> = Pager(
        PagingConfig(pageSize = 20)
    ){
        pagingSourceFactory
    }.flow.stateIn(viewModelScope, SharingStarted.Lazily,PagingData.empty())

    fun load() = viewModelScope.launch(Dispatchers.IO){
        //flof = networkRep.getPagedPage().cachedIn(viewModelScope)


//        if (!response.isSuccessful) return@launch
//        _liveData.postValue(response.body()?.results)
    }
}