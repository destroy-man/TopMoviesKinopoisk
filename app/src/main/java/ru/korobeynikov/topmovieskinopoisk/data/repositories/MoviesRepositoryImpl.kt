package ru.korobeynikov.topmovieskinopoisk.data.repositories

import retrofit2.Retrofit
import ru.korobeynikov.topmovieskinopoisk.data.webapi.KinopoiskAPI
import ru.korobeynikov.topmovieskinopoisk.domain.MovieElementDomain
import ru.korobeynikov.topmovieskinopoisk.domain.MovieListElementDomain
import ru.korobeynikov.topmovieskinopoisk.domain.MoviesRepository

class MoviesRepositoryImpl(private val retrofit: Retrofit) : MoviesRepository {

    override suspend fun getTopMovies(): List<MovieListElementDomain> {
        val kinopoiskAPI = retrofit.create(KinopoiskAPI::class.java)
        val listMovies = kinopoiskAPI.getTopMovies().movies.map { movie ->
            MovieListElementDomain(
                movie.filmId,
                movie.nameRu,
                movie.genres.map { it.genre },
                movie.year,
                movie.posterUrl
            )
        }
        return listMovies
    }

    override suspend fun getMovie(id: Int): MovieElementDomain {
        val kinopoiskAPI = retrofit.create(KinopoiskAPI::class.java)
        val movie = kinopoiskAPI.getMovie(id)
        return MovieElementDomain(
            movie.posterUrl,
            movie.nameRu,
            movie.description,
            movie.genres.map { it.genre },
            movie.countries.map { it.country }
        )
    }
}