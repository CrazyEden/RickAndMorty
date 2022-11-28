package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.characters.AllCharactersFragment
import com.example.rickandmorty.ui.episodes.AllEpisodesFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationView = findViewById<NavigationView>(R.id.navigation)
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_all_characters->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view_tag,AllCharactersFragment()).commit()
                }
                R.id.item_all_episodes->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view_tag,AllEpisodesFragment()).commit()
                }
            }
            true
        }
    }

}