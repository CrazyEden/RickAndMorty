package com.example.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.rickandmorty.data.repositories.LocalStorageRepository
import com.example.rickandmorty.data.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AllCharactersViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val localStorageRepository: LocalStorageRepository
) : ViewModel() {
    fun load(name:String? = null,
             status:String? = null,
             gender:String? = null,
            mFilter:Boolean = false): Flow<PagingData<CharacterUiModel>> {

        return networkRepository.getFlof(
            name = name?: localStorageRepository.getFilterText(),
            status = status?: localStorageRepository.getStateStatusFilter(),
            gender = gender?: localStorageRepository.getStateGenderFilter(),
            mFilter = mFilter
        ).cachedIn(viewModelScope).map {
            it.insertSeparators { uiModel1: CharacterUiModel?, uiModel2: CharacterUiModel? ->
                if (uiModel2 == null) //end of view
                    return@insertSeparators null

                if (uiModel1 is CharacterUiModel.Header || uiModel2 is CharacterUiModel.Header)
                    return@insertSeparators null

                val entity2 = (uiModel2 as CharacterUiModel.Item).entity
                if (uiModel1 == null) { // start of list
                    return@insertSeparators if (entity2.id!! <= 5) CharacterUiModel.Header("Main Family")
                    else CharacterUiModel.Header(entity2.name?.get(0).toString())
                }

                if ( entity2.id!! <= 5) //for main family header
                    return@insertSeparators null

                val entity1 = (uiModel1 as CharacterUiModel.Item).entity

                val char1 = entity1.name?.get(0)
                val char2 = entity2.name?.get(0)

                return@insertSeparators if (char1 != char2 ) CharacterUiModel.Header(char2.toString())
                else null
            }
        }
    }


    fun getText()= localStorageRepository.getFilterText()

    fun getStatus()= localStorageRepository.getStateStatusFilter()

    fun getGender()= localStorageRepository.getStateGenderFilter()

    fun getSwitcher()= localStorageRepository.getStateSwitcher()
    fun saveSearchState(text:String, gender:String, status:String,switcherState:Boolean){
        localStorageRepository.saveSearchPanelState(
            text =text,
            gender=gender,
            status=status,
            switcher = switcherState)
    }


}