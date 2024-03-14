package ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.topmovieskinopoisk.domain.DatabaseRepository
import ru.korobeynikov.topmovieskinopoisk.domain.NetworkRepository
import javax.inject.Inject

class MoviesViewModelFactory @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MoviesViewModel(networkRepository, databaseRepository) as T
}