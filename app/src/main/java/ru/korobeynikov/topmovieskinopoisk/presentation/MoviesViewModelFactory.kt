package ru.korobeynikov.topmovieskinopoisk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.topmovieskinopoisk.domain.MoviesRepository
import javax.inject.Inject

class MoviesViewModelFactory @Inject constructor(private val repository: MoviesRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(repository) as T
    }
}