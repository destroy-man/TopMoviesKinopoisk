package ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.korobeynikov.topmovieskinopoisk.domain.NetworkRepository
import ru.korobeynikov.topmovieskinopoisk.presentation.movie.MovieElement
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.MovieListElement

class NetworkViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _topMoviesState = mutableStateOf(emptyList<MovieListElement>())
    val topMoviesState: State<List<MovieListElement>> = _topMoviesState

    private val _topMoviesErrorState = mutableStateOf(false)
    val topMoviesErrorState: State<Boolean> = _topMoviesErrorState

    private val _movieState = mutableStateOf(MovieElement())
    val movieState: State<MovieElement> = _movieState

    private val _movieErrorState = mutableStateOf(false)
    val movieErrorState: State<Boolean> = _movieErrorState

    suspend fun getTopMovies() = viewModelScope.launch {
        try {
            _topMoviesState.value = networkRepository.getTopMovies().map { movie ->
                MovieListElement(
                    movie.filmId,
                    movie.nameRu,
                    movie.posterUrl,
                    movie.genres,
                    movie.year,
                    false
                )
            }
            if (_topMoviesErrorState.value)
                _topMoviesErrorState.value = false
        } catch (e: Exception) {
            _topMoviesErrorState.value = true
        }
    }.join()

    suspend fun getMovie(id: Int) = viewModelScope.launch {
        try {
            val movie = networkRepository.getMovie(id)
            _movieState.value = MovieElement(
                movie.posterUrl,
                movie.nameRu,
                movie.description,
                movie.genres,
                movie.countries
            )
            if (_movieErrorState.value)
                _movieErrorState.value = false
        } catch (e: Exception) {
            _movieErrorState.value = true
        }
    }.join()
}