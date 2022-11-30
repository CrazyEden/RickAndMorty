package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class ResponseAllLocation(
    @SerializedName("info"    ) var info    : Info?,
    @SerializedName("results" ) var results : ArrayList<Location>
)