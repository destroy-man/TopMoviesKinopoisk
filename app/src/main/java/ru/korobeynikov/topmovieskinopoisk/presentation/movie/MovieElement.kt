package ru.korobeynikov.topmovieskinopoisk.presentation.movie

data class MovieElement(
    val image: String = "",
    val title: String = "",
    val description: String = "",
    val genres: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
)