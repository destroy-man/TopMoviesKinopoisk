package ru.korobeynikov.topmovieskinopoisk.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.korobeynikov.topmovieskinopoisk.di.App
import ru.korobeynikov.topmovieskinopoisk.presentation.movie.MovieScreen
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.MovieListElement
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.TopListScreenPopular
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.TopListScreenSaved
import ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels.MoviesViewModel
import ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels.MoviesViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    private var isPopularList = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).moviesComponent.injectMainActivity(this)
        setContent {
            val moviesViewModel by viewModels<MoviesViewModel> {
                moviesViewModelFactory
            }
            val configuration = LocalConfiguration.current
            Column(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "listMovies/popular",
                    modifier = Modifier.weight(1f)
                ) {
                    composable("listMovies/{type}", arguments = listOf(navArgument("type") {
                        type = NavType.StringType
                    })) {
                        LaunchedEffect(key1 = Unit) {
                            moviesViewModel.getTopMovies()
                            moviesViewModel.getSavedMovies()
                        }
                        val type = it.arguments?.getString("type") ?: "popular"
                        isPopularList = type == "popular"
                        ListMoviesNavigation(
                            navController,
                            moviesViewModel,
                            configuration
                        )
                    }
                    composable("movie/{id}", arguments = listOf(navArgument("id") {
                        type = NavType.StringType
                    })) {
                        LaunchedEffect(key1 = Unit) {
                            moviesViewModel.getTopMovies()
                            moviesViewModel.getSavedMovies()
                        }
                        val id = it.arguments?.getString("id")?.toInt()
                        if (id != null)
                            MovieNavigation(
                                id,
                                navController,
                                moviesViewModel,
                                configuration
                            )
                    }
                }
            }
        }
    }

    @Composable
    fun ListMoviesNavigation(
        navController: NavHostController,
        moviesViewModel: MoviesViewModel,
        configuration: Configuration,
    ) {
        if (isPopularList) {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                TopListScreenPopular(navController, moviesViewModel) {
                    addMovie(moviesViewModel, it, navController)
                }
            else
                Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                    TopListScreenPopular(navController, moviesViewModel) {
                        addMovie(moviesViewModel, it, navController)
                    }
                }
        } else {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                TopListScreenSaved(navController, moviesViewModel) {
                    addMovie(moviesViewModel, it, navController)
                }
            else
                Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                    TopListScreenSaved(navController, moviesViewModel) {
                        addMovie(moviesViewModel, it, navController)
                    }
                }
        }
    }

    private fun addMovie(
        moviesViewModel: MoviesViewModel,
        movie: MovieListElement,
        navController: NavHostController,
    ) {
        movie.isSaved = !movie.isSaved
        moviesViewModel.addMovie(movie).invokeOnCompletion {
            if (isPopularList)
                navController.navigate("listMovies/popular")
            else
                navController.navigate("listMovies/saved")
        }
    }

    @Composable
    fun MovieNavigation(
        id: Int,
        navController: NavHostController,
        moviesViewModel: MoviesViewModel,
        configuration: Configuration,
    ) {
        val modifier = Modifier.padding(start = 30.dp, bottom = 10.dp, end = 30.dp)
        val onNavigateBack: () -> Unit = {
            navController.popBackStack()
        }
        val onNavigateToMovie: () -> Unit = {
            navController.navigate("movie/$id")
        }
        LaunchedEffect(key1 = Unit) {
            moviesViewModel.getMovie(id)
        }
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            MovieScreen(moviesViewModel, modifier, onNavigateBack, onNavigateToMovie)
        else
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    if (isPopularList)
                        TopListScreenPopular(navController, moviesViewModel) {
                            addMovie(moviesViewModel, it, navController)
                        }
                    else
                        TopListScreenSaved(navController, moviesViewModel) {
                            addMovie(moviesViewModel, it, navController)
                        }
                }
                Column(modifier = Modifier.weight(1f)) {
                    MovieScreen(moviesViewModel, modifier, onNavigateBack, onNavigateToMovie)
                }
            }
    }
}