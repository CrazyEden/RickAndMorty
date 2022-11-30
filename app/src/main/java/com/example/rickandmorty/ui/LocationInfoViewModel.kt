package com.example.rickandmorty.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.data.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationInfoViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
):ViewModel() {
    private val _liveData = MutableLiveData<List<Entity>>()
    val listLiveData = _liveData
    private val _locationLiveData = MutableLiveData<Location>()
    val locationLiveData = _locationLiveData

    fun load(list:List<String>)=viewModelScope.launch(Dispatchers.IO){
        val listOfInts = mutableListOf<Int>()
        list.forEach {
            listOfInts.add(it.drop(42).toInt())
        }
        val res = listOfInts.joinToString(",")
        val response = networkRepository.getEntityListByListIds(res)
        _liveData.postValue(response.body())
    }

    fun loadLocationById(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = networkRepository.getSingleLocation(id)
            _locationLiveData.postValue(res.body())
        }
    }
}