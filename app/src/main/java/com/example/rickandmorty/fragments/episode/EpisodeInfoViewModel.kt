package com.example.rickandmorty.fragments.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.data.reps.NetworkRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeInfoViewModel @Inject constructor(
    private val networkRep: NetworkRep
) :ViewModel() {
    private val _episodeLiveData = MutableLiveData<Episode>()
    val episodeLiveData = _episodeLiveData
    private val _entityLiveData = MutableLiveData<List<Entity>>()
    val entityLiveData = _entityLiveData


    fun getEpisode(id:Int)= viewModelScope.launch(Dispatchers.IO){
        val response = networkRep.getEpisode(id)
        if(!response.isSuccessful) throw Exception("xz")
        _episodeLiveData.postValue(response.body())
    }
    fun getMultipleCharacters(listOfUrls:List<String>)= viewModelScope.launch(Dispatchers.IO){
        val list = mutableListOf<Int>()
        listOfUrls.forEach {
            list.add(it.drop(42).toInt())
        }
        val str = list.joinToString(",")
        val response = networkRep.getEntityListByListIds(str)

        _entityLiveData.postValue(response.body())
    }
}