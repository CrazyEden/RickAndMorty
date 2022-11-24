package com.example.rickandmorty.data.reps

import android.content.SharedPreferences
import com.example.rickandmorty.fragments.main.MainFragment
import javax.inject.Inject

class LocalStorageRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun getFilterText():String? =
        sharedPreferences.getString(MainFragment.KEY_TEXT_FILTER,"")
    fun saveFilterText(text:String) =
        sharedPreferences.edit().putString(MainFragment.KEY_TEXT_FILTER, text)


    fun getStateGenderFilter():String?=
        sharedPreferences.getString(MainFragment.KEY_GENDER_FILTER,"")
    fun saveGenderText(gender:String) =
        sharedPreferences.edit().putString(MainFragment.KEY_GENDER_FILTER,gender)

    fun getStateStatusFilter():String?=
        sharedPreferences.getString(MainFragment.KEY_STATUS_FILTER,"")
    fun saveStatusText(status:String) =
        sharedPreferences.edit().putString(MainFragment.KEY_STATUS_FILTER,status)


}