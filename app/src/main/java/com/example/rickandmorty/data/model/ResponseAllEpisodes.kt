package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class ResponseAllEpisodes (
    @SerializedName("info"    ) var info    : Info?,
    @SerializedName("results" ) var results : List<Episode> = listOf()
)