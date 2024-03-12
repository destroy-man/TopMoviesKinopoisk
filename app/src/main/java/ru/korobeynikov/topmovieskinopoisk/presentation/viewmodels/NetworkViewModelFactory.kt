package ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.topmovieskinopoisk.domain.NetworkRepository
import javax.inject.Inject

class NetworkViewModelFactory @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        NetworkViewModel(networkRepository) as T
}