package ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.korobeynikov.topmovieskinopoisk.domain.DatabaseRepository
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
                it.year
            )
        }
    }
}