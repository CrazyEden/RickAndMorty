package com.example.rickandmorty.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.rickandmorty.CharacterUiModel
import com.example.rickandmorty.data.reps.LocalStorageRepository
import com.example.rickandmorty.data.reps.NetworkRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRep: NetworkRep,
    private val localStorageRepository: LocalStorageRepository
) : ViewModel() {
    fun load(name:String? = null,
             status:String? = null,
             gender:String? = null,
            mFilter:Boolean = false): Flow<PagingData<CharacterUiModel>> {

        return networkRep.getFlof(
            name = name?: localStorageRepository.getFilterText(),
            status = status?: localStorageRepository.getStateStatusFilter(),
            gender = gender?: localStorageRepository.getStateGenderFilter(),
            mFilter = mFilter
        ).cachedIn(viewModelScope).map {
            it.insertSeparators { characterUiModel: CharacterUiModel?, characterUiModel2: CharacterUiModel? ->
                if (characterUiModel == null || characterUiModel2 == null) return@insertSeparators null
                if (characterUiModel is CharacterUiModel.Header || characterUiModel2 is CharacterUiModel.Header)
                    return@insertSeparators null
                val entity1 = (characterUiModel as CharacterUiModel.Item).entity.name?.get(0)
                val entity2 = (characterUiModel2 as CharacterUiModel.Item).entity.name?.get(0)
                return@insertSeparators if (entity1 != entity2){
                    CharacterUiModel.Header(entity2.toString())
                } else null
            }
        }
    }

    fun saveText(text:String){
        localStorageRepository.saveFilterText(text)
    }
    fun getText()= localStorageRepository.getFilterText()


    fun saveStatus(status:String){
        localStorageRepository.saveStatusText(status)
    }
    fun getStatus()= localStorageRepository.getStateStatusFilter()


    fun saveGender(gender:String){
        localStorageRepository.saveGenderText(gender)
    }
    fun getGender()= localStorageRepository.getStateGenderFilter()




}