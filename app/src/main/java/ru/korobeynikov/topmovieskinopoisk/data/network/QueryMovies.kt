package ru.korobeynikov.topmovieskinopoisk.data.network

import com.google.gson.annotations.SerializedName

data class QueryMovies(
    @SerializedName("films")
    val movies: List<MovieListElementData>,
)
