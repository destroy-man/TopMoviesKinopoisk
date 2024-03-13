package ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.korobeynikov.topmovieskinopoisk.domain.DatabaseRepository
import ru.korobeynikov.topmovieskinopoisk.domain.MovieListElementDomain
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.MovieListElement

class DatabaseViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {

    private val _savedMoviesState = mutableStateOf(emptyList<MovieListElement>())
    val savedMoviesState: State<List<MovieListElement>> = _savedMoviesState

    fun getSavedMovies() = viewModelScope.launch {
        _savedMoviesState.value = databaseRepository.getMovies().map {
            MovieListElement(
                it.filmId,
                it.nameRu,
                it.posterUrl,
                it.genres,
                it.year,
                true
            )
        }
    }

    fun addMovie(movie: MovieListElement) = viewModelScope.launch {
        if (databaseRepository.getMovie(movie.id) == null)
            databaseRepository.addMovie(
                MovieListElementDomain(
                    movie.id,
                    movie.name,
                    movie.genres,
                    movie.year,
                    movie.image
                )
            )
        else
            deleteMovie(movie)
    }

    private fun deleteMovie(movie: MovieListElement) = viewModelScope.launch {
        if (databaseRepository.getMovie(movie.id) != null)
            databaseRepository.deleteMovie(
                MovieListElementDomain(
                    movie.id,
                    movie.name,
                    movie.genres,
                    movie.year,
                    movie.image
                )
            )
    }

    suspend fun getMovie(movie: MovieListElement) = viewModelScope.async {
        databaseRepository.getMovie(movie.id) != null
    }.await()
}