package ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.korobeynikov.topmovieskinopoisk.domain.DatabaseRepository
import ru.korobeynikov.topmovieskinopoisk.domain.MovieListElementDomain
import ru.korobeynikov.topmovieskinopoisk.domain.NetworkRepository
import ru.korobeynikov.topmovieskinopoisk.presentation.movie.MovieElement
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.MovieListElement

class MoviesViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    val listTopMovies = ArrayList<MovieListElement>()
    val listSavedMovies = ArrayList<MovieListElement>()

    private val _topMoviesState = mutableStateOf(emptyList<MovieListElement>())
    val topMoviesState: State<List<MovieListElement>> = _topMoviesState

    private val _topMoviesErrorState = mutableStateOf(false)
    val topMoviesErrorState: State<Boolean> = _topMoviesErrorState

    private val _movieState = mutableStateOf(MovieElement())
    val movieState: State<MovieElement> = _movieState

    private val _movieErrorState = mutableStateOf(false)
    val movieErrorState: State<Boolean> = _movieErrorState

    private val _savedMoviesState = mutableStateOf(emptyList<MovieListElement>())
    val savedMoviesState: State<List<MovieListElement>> = _savedMoviesState

    suspend fun getTopMovies() = viewModelScope.launch {
        try {
            _topMoviesState.value = networkRepository.getTopMovies().map { movie ->
                MovieListElement(
                    movie.filmId,
                    movie.nameRu,
                    movie.posterUrl,
                    movie.genres,
                    movie.year,
                    databaseRepository.getMovie(movie.filmId) != null
                )
            }
            if (_topMoviesErrorState.value)
                _topMoviesErrorState.value = false
            listTopMovies.clear()
            listTopMovies.addAll(_topMoviesState.value)
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

    fun loadTopMoviesState(listMovies: List<MovieListElement>) {
        _topMoviesState.value = listMovies
    }

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
        listSavedMovies.clear()
        listSavedMovies.addAll(_savedMoviesState.value)
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

    fun loadSavedMoviesState(listMovies: List<MovieListElement>) {
        _savedMoviesState.value = listMovies
    }
}