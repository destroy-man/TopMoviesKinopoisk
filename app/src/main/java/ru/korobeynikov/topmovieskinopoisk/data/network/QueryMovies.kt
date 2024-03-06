package ru.korobeynikov.topmovieskinopoisk.data.network

import com.google.gson.annotations.SerializedName
import ru.korobeynikov.topmovieskinopoisk.data.network.MovieListElementData

data class QueryMovies(
    @SerializedName("pagesCount")
    val pagesCount:Int,
    @SerializedName("films")
    val movies:List<MovieListElementData>
)
