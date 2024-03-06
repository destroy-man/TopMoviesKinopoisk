package ru.korobeynikov.topmovieskinopoisk.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import ru.korobeynikov.topmovieskinopoisk.data.network.KinopoiskAPI
import ru.korobeynikov.topmovieskinopoisk.domain.MovieListElementDomain
import ru.korobeynikov.topmovieskinopoisk.domain.MoviesRepository

class MoviesRepositoryImpl(private val retrofit:Retrofit): MoviesRepository {
    override suspend fun getTopMovies():List<MovieListElementDomain>{

        val kinopoiskAPI=retrofit.create(KinopoiskAPI::class.java)
        val listMovies=kinopoiskAPI.getTopMovies().movies.map {
            MovieListElementDomain(
                it.filmId,
                it.nameRu,
                it.genres,
                it.year,
                it.posterUrl
            )
        }
        return listMovies
    }
}