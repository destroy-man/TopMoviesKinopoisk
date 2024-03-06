package ru.korobeynikov.topmovieskinopoisk.domain

interface MoviesRepository {
    suspend fun getTopMovies():List<MovieListElementDomain>
}