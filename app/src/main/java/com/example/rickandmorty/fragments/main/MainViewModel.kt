package com.example.rickandmorty.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.reps.NetworkRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRep: NetworkRep
) : ViewModel() {
    fun load(name:String? = null,
             status:String? = null,
             gender:String? = null): Flow<PagingData<Entity>> {

        return networkRep.getFlof(
            name = name,
            status = status,
            gender = gender,
        ).cachedIn(viewModelScope)
    }





}