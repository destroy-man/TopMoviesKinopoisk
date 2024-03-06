package ru.korobeynikov.topmovieskinopoisk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.korobeynikov.topmovieskinopoisk.di.App
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).moviesComponent.injectMainActivity(this)
        setContent {
            TopListScreen(viewModel(factory = moviesViewModelFactory))
        }
    }
}