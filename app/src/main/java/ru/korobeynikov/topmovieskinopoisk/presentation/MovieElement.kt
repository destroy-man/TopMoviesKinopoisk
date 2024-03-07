package ru.korobeynikov.topmovieskinopoisk.presentation

data class MovieElement(
    val image:String,
    val title:String,
    val description:String,
    val genres:List<String>,
    val countries:List<String>
)
