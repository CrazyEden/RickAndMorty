package com.example.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmorty.fragments.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, MainFragment())
                .commit()
        }
    }
}