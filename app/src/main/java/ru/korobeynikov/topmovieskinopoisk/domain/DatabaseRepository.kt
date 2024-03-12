package ru.korobeynikov.topmovieskinopoisk.domain

interface DatabaseRepository {

    suspend fun getMovies(): List<MovieListElementDomain>

    suspend fun addMovie(movie: MovieListElementDomain)

    suspend fun deleteMovie(movie: MovieListElementDomain)
}