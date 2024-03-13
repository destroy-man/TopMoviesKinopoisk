package ru.korobeynikov.topmovieskinopoisk.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.korobeynikov.topmovieskinopoisk.di.App
import ru.korobeynikov.topmovieskinopoisk.presentation.movie.MovieScreen
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.TopListScreenPopular
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.TopListScreenSaved
import ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels.DatabaseViewModelFactory
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
                            navController = navController,
                            configuration = configuration,
                        )
                    }
                    composable("movie/{id}", arguments = listOf(navArgument("id") {
                        type = NavType.StringType
                    })) {
                        val id = it.arguments?.getString("id")?.toInt()
                        MovieNavigation(
                            id = id,
                            navController = navController,
                            configuration = configuration,
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ListMoviesNavigation(
        navController: NavHostController,
        configuration: Configuration,
    ) {
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            TopListScreen(navController = navController)
        else
            Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                TopListScreen(navController = navController)
            }
    }

    @Composable
    fun MovieNavigation(
        id: Int?,
        navController: NavHostController,
        configuration: Configuration,
    ) {
        if (id != null) {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                MovieScreen(
                    viewModel = viewModel(factory = networkViewModelFactory),
                    id = id,
                    modifier = Modifier.padding(
                        start = 30.dp,
                        bottom = 10.dp,
                        end = 30.dp
                    ),
                    onNavigateBack = {
                        navController.popBackStack()
                    }, onNavigateToMovie = {
                        navController.navigate("movie/$id")
                    }
                )
            } else {
                Row {
                    Column(modifier = Modifier.weight(1f)) {
                        TopListScreen(navController = navController)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        MovieScreen(
                            viewModel = viewModel(factory = networkViewModelFactory),
                            id = id,
                            modifier = Modifier.padding(
                                start = 30.dp,
                                bottom = 10.dp,
                                end = 30.dp
                            ),
                            onNavigateBack = {
                                navController.popBackStack()
                            }, onNavigateToMovie = {
                                navController.navigate("movie/$id")
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun TopListScreen(navController: NavHostController) {
        if (isPopularList)
            TopListScreenPopular(
                networkViewModel = viewModel(factory = networkViewModelFactory),
                databaseViewModel = viewModel(factory = databaseViewModelFactory),
                navController = navController
            )
        else
            TopListScreenSaved(
                databaseViewModel = viewModel(factory = databaseViewModelFactory),
                navController = navController
            )
    }
}