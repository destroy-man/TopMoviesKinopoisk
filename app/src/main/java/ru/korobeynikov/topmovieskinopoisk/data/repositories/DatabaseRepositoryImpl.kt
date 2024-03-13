package ru.korobeynikov.topmovieskinopoisk.data.repositories

import ru.korobeynikov.topmovieskinopoisk.data.database.KinopoiskDatabase
import ru.korobeynikov.topmovieskinopoisk.data.database.MovieTable
import ru.korobeynikov.topmovieskinopoisk.domain.DatabaseRepository
import ru.korobeynikov.topmovieskinopoisk.domain.MovieListElementDomain

class DatabaseRepositoryImpl(private val kinopoiskDatabase: KinopoiskDatabase) :
    DatabaseRepository {

    override suspend fun getMovies() = kinopoiskDatabase.moviesDao().getMovies().map {
        MovieListElementDomain(
            it.id,
            it.name,
            it.genres,
            it.year,
            it.image
        )
    }

    override suspend fun getMovie(id: Int): MovieListElementDomain? {
        val movie = kinopoiskDatabase.moviesDao().getMovie(id)
        return if (movie != null)
            MovieListElementDomain(
                movie.id,
                movie.name,
                movie.genres,
                movie.year,
                movie.image
            )
        else null
    }

    override suspend fun addMovie(movie: MovieListElementDomain) =
        kinopoiskDatabase.moviesDao().addMovie(
            MovieTable(
                movie.filmId,
                movie.nameRu,
                movie.posterUrl,
                movie.genres,
                movie.year
            )
        )

    override suspend fun deleteMovie(movie: MovieListElementDomain) =
        kinopoiskDatabase.moviesDao().deleteMovie(
            MovieTable(
                movie.filmId,
                movie.nameRu,
                movie.posterUrl,
                movie.genres,
                movie.year
            )
        )
}