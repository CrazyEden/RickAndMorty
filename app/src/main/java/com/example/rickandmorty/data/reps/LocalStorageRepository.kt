package com.example.rickandmorty.data.reps

import android.content.SharedPreferences
import com.example.rickandmorty.fragments.characters.AllCharactersFragment
import javax.inject.Inject

class LocalStorageRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun getFilterText():String? =
        sharedPreferences.getString(AllCharactersFragment.KEY_TEXT_FILTER,"")
    fun saveFilterText(text:String) =
        sharedPreferences.edit().putString(AllCharactersFragment.KEY_TEXT_FILTER, text)


    fun getStateGenderFilter():String?=
        sharedPreferences.getString(AllCharactersFragment.KEY_GENDER_FILTER,"")
    fun saveGenderText(gender:String) =
        sharedPreferences.edit().putString(AllCharactersFragment.KEY_GENDER_FILTER,gender)

    fun getStateStatusFilter():String?=
        sharedPreferences.getString(AllCharactersFragment.KEY_STATUS_FILTER,"")
    fun saveStatusText(status:String) =
        sharedPreferences.edit().putString(AllCharactersFragment.KEY_STATUS_FILTER,status)


}