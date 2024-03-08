package ru.korobeynikov.topmovieskinopoisk.domain

data class MovieListElementDomain(
    val filmId: Int,
    val nameRu: String,
    val genres: List<String>,
    val year: Int,
    val posterUrl: String,
)