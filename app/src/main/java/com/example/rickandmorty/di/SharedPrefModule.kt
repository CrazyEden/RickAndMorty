package com.example.rickandmorty.di

import android.content.Context
import android.content.SharedPreferences
import com.example.rickandmorty.fragments.main.MainFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context):SharedPreferences =
        context.getSharedPreferences(MainFragment.NAME_SHARED_PREFERENCES,Context.MODE_PRIVATE)

}