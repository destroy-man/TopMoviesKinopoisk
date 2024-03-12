package ru.korobeynikov.topmovieskinopoisk.domain

interface NetworkRepository {

    suspend fun getTopMovies(): List<MovieListElementDomain>

    suspend fun getMovie(id: Int): MovieElementDomain
}