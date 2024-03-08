package ru.korobeynikov.topmovieskinopoisk.data.network

import com.google.gson.annotations.SerializedName

data class MovieElementData(
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("countries")
    val countries: List<Country>,
)