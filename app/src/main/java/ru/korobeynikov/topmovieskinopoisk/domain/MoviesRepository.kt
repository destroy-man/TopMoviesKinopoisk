package ru.korobeynikov.topmovieskinopoisk.domain

interface MoviesRepository {

    suspend fun getTopMovies(): List<MovieListElementDomain>

    suspend fun getMovie(id: Int): MovieElementDomain
}