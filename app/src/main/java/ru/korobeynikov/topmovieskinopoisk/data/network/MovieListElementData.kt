package ru.korobeynikov.topmovieskinopoisk.data.network

import com.google.gson.annotations.SerializedName

data class MovieListElementData(
    @SerializedName("filmId")
    val filmId:Int,
    @SerializedName("nameRu")
    val nameRu:String,
    @SerializedName("year")
    val year:Int,
    @SerializedName("genres")
    val genres:List<Genre>,
    @SerializedName("posterUrl")
    val posterUrl:String,
)