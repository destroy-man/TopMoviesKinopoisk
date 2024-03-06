package ru.korobeynikov.topmovieskinopoisk.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import ru.korobeynikov.topmovieskinopoisk.domain.MoviesRepository

class MoviesViewModel(private val repository: MoviesRepository):ViewModel() {

    private val _topMoviesState= mutableStateOf(emptyList<MovieListElement>())
    val topMoviesState:State<List<MovieListElement>> = _topMoviesState

    fun getTopMovies(){
        viewModelScope.launch {
            _topMoviesState.value=repository.getTopMovies().map { movie ->
                MovieListElement(
                    movie.filmId,
                    movie.nameRu,
                    movie.posterUrl,
                    movie.genres.map { it.genre },
                    movie.year
                )
            }
        }
    }
}