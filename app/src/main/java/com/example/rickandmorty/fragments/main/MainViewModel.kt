package com.example.rickandmorty.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmorty.data.reps.NetworkRep
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRep: NetworkRep
) : ViewModel() {

    val sflof = networkRep.getFlof().cachedIn(viewModelScope)



}