package ru.korobeynikov.topmovieskinopoisk.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.korobeynikov.topmovieskinopoisk.domain.MoviesRepository
import ru.korobeynikov.topmovieskinopoisk.presentation.movie.MovieElement
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.MovieListElement

class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _topMoviesState = mutableStateOf(emptyList<MovieListElement>())
    val topMoviesState: State<List<MovieListElement>> = _topMoviesState

    private val _movieState = mutableStateOf(MovieElement())
    val movieState: State<MovieElement> = _movieState

    fun getTopMovies() = viewModelScope.launch {
        _topMoviesState.value = repository.getTopMovies().map { movie ->
            MovieListElement(
                movie.filmId,
                movie.nameRu,
                movie.posterUrl,
                movie.genres,
                movie.year
            )
        }
    }

    fun getMovie(id: Int) = viewModelScope.launch {
        val movie = repository.getMovie(id)
        _movieState.value = MovieElement(
            movie.posterUrl,
            movie.nameRu,
            movie.description,
            movie.genres,
            movie.countries
        )
    }
}