package ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies

data class MovieListElement(
    val id: Int,
    val name: String,
    val image: String,
    val genres: List<String>,
    val year: Int,
    var isSaved: Boolean,
)
