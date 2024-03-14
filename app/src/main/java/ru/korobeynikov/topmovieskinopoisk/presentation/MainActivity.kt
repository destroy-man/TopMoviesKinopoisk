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
import ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels.DatabaseViewModel
import ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels.DatabaseViewModelFactory
import ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels.NetworkViewModel
import ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels.NetworkViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkViewModelFactory: NetworkViewModelFactory

    @Inject
    lateinit var databaseViewModelFactory: DatabaseViewModelFactory

    private var isPopularList = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).moviesComponent.injectMainActivity(this)
        setContent {
            val networkViewModel by viewModels<NetworkViewModel> {
                networkViewModelFactory
            }
            val databaseViewModel by viewModels<DatabaseViewModel> {
                databaseViewModelFactory
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
                        val type = it.arguments?.getString("type") ?: "popular"
                        isPopularList = type == "popular"
                        ListMoviesNavigation(
                            navController,
                            networkViewModel,
                            databaseViewModel,
                            configuration
                        )
                    }
                    composable("movie/{id}", arguments = listOf(navArgument("id") {
                        type = NavType.StringType
                    })) {
                        val id = it.arguments?.getString("id")?.toInt()
                        if (id != null)
                            MovieNavigation(
                                id,
                                navController,
                                networkViewModel,
                                databaseViewModel,
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
        networkViewModel: NetworkViewModel,
        databaseViewModel: DatabaseViewModel,
        configuration: Configuration,
    ) {
        if (isPopularList) {
            LaunchedEffect(key1 = Unit) {
                networkViewModel.getTopMovies()
            }
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                TopListScreenPopular(navController, networkViewModel) {
                    addMovie(databaseViewModel, it, navController)
                }
            else
                Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                    TopListScreenPopular(navController, networkViewModel) {
                        addMovie(databaseViewModel, it, navController)
                    }
                }
        } else {
            LaunchedEffect(key1 = Unit) {
                databaseViewModel.getSavedMovies()
            }
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                TopListScreenSaved(navController, databaseViewModel) {
                    addMovie(databaseViewModel, it, navController)
                }
            else
                Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                    TopListScreenSaved(navController, databaseViewModel) {
                        addMovie(databaseViewModel, it, navController)
                    }
                }
        }
    }

    private fun addMovie(
        databaseViewModel: DatabaseViewModel,
        movie: MovieListElement,
        navController: NavHostController,
    ) {
        databaseViewModel.addMovie(movie).invokeOnCompletion {
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
        networkViewModel: NetworkViewModel,
        databaseViewModel: DatabaseViewModel,
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
            networkViewModel.getMovie(id)
        }
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            MovieScreen(networkViewModel, modifier, onNavigateBack, onNavigateToMovie)
        else {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    if (isPopularList) {
                        LaunchedEffect(key1 = Unit) {
                            networkViewModel.getTopMovies()
                        }
                        TopListScreenPopular(navController, networkViewModel) {
                            addMovie(databaseViewModel, it, navController)
                        }
                    } else {
                        LaunchedEffect(key1 = Unit) {
                            databaseViewModel.getSavedMovies()
                        }
                        TopListScreenSaved(navController, databaseViewModel) {
                            addMovie(databaseViewModel, it, navController)
                        }
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    MovieScreen(networkViewModel, modifier, onNavigateBack, onNavigateToMovie)
                }
            }
        }
    }
}