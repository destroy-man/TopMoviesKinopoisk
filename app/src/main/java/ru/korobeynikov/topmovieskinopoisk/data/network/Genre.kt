package ru.korobeynikov.topmovieskinopoisk.data.network

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("genre")
    val genre:String
)
