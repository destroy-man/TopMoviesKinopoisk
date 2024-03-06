package ru.korobeynikov.topmovieskinopoisk.data.network

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country")
    val country:String
)
