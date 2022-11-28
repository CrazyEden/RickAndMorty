package com.example.rickandmorty.data.repositories

import android.content.SharedPreferences
import com.example.rickandmorty.ui.characters.AllCharactersFragment
import javax.inject.Inject

class LocalStorageRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun getFilterText():String =
        sharedPreferences.getString(AllCharactersFragment.KEY_TEXT_FILTER,"")!!

    fun getStateGenderFilter():String=
        sharedPreferences.getString(AllCharactersFragment.KEY_GENDER_FILTER,"")!!

    fun getStateStatusFilter():String=
        sharedPreferences.getString(AllCharactersFragment.KEY_STATUS_FILTER,"")!!

    fun getStateSwitcher(): Boolean =
        sharedPreferences.getBoolean(AllCharactersFragment.KEY_SWITCHER_FILTER, false)

    fun saveSearchPanelState(text:String, gender:String, status:String, switcher:Boolean){
        sharedPreferences.edit()
            .putString(AllCharactersFragment.KEY_TEXT_FILTER, text)
            .putString(AllCharactersFragment.KEY_GENDER_FILTER,gender)
            .putString(AllCharactersFragment.KEY_STATUS_FILTER,status)
            .putBoolean(AllCharactersFragment.KEY_SWITCHER_FILTER, switcher).apply()
    }


}