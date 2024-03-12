package ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.topmovieskinopoisk.domain.DatabaseRepository
import javax.inject.Inject

class DatabaseViewModelFactory @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DatabaseViewModel(databaseRepository) as T
}