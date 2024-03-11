package ru.korobeynikov.topmovieskinopoisk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.korobeynikov.topmovieskinopoisk.di.App
import ru.korobeynikov.topmovieskinopoisk.presentation.movie.MovieScreen
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.TopListScreen
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).moviesComponent.injectMainActivity(this)
        setContent {
            val moviesViewModel by viewModels<MoviesViewModel> {
                moviesViewModelFactory
            }
            Column(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "listMovies",
                    modifier = Modifier.weight(1f)
                ) {
                    composable("listMovies") {
                        moviesViewModel.getTopMovies()
                        val isError by moviesViewModel.topMoviesErrorState
                        if (isError)
                            ErrorScreen(navController = navController, errorSource = "listMovies")
                        else {
                            val listMovies by moviesViewModel.topMoviesState
                            TopListScreen(listMovies, navController)
                        }
                    }
                    composable("movie/{id}", arguments = listOf(navArgument("id") {
                        type = NavType.StringType
                    })) {
                        val id = it.arguments?.getString("id")?.toInt()
                        if (id != null) {
                            moviesViewModel.getMovie(id)
                            val isError by moviesViewModel.movieErrorState
                            if (isError)
                                ErrorScreen(
                                    navController = navController,
                                    errorSource = "movie/$id"
                                )
                            else {
                                val movie by moviesViewModel.movieState
                                MovieScreen(
                                    movie = movie,
                                    modifier = Modifier.padding(
                                        start = 30.dp,
                                        bottom = 10.dp,
                                        end = 30.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}