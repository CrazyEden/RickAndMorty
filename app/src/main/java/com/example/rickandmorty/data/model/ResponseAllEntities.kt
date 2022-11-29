package com.example.rickandmorty.data.model
import com.google.gson.annotations.SerializedName


data class ResponseAllEntities (

    @SerializedName("info"    ) var info    : Info?,
    @SerializedName("results" ) var results : List<Entity> = listOf()

)

