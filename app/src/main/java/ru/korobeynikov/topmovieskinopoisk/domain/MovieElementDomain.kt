package ru.korobeynikov.topmovieskinopoisk.domain

data class MovieElementDomain(
    val posterUrl: String,
    val nameRu: String,
    val description: String,
    val genres: List<String>,
    val countries: List<String>,
)
