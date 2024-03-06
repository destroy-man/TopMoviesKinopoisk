package ru.korobeynikov.topmovieskinopoisk.domain

import ru.korobeynikov.topmovieskinopoisk.data.network.Genre

data class MovieListElementDomain(
    val filmId:Int,
    val nameRu:String,
    val genres:List<Genre>,
    val year:Int,
    val posterUrl:String,
)
