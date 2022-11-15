package com.example.rickandmorty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.reps.NetworkRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRep: NetworkRep
) : ViewModel() {
    private val _liveData = MutableLiveData<List<Entity>>()
    val liveData = _liveData
    fun load() = viewModelScope.launch(Dispatchers.IO){
        val response = networkRep.loadFirstStack()
        if (!response.isSuccessful) return@launch
        _liveData.postValue(response.body()?.results)
    }
}