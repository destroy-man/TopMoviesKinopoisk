package ru.korobeynikov.topmovieskinopoisk.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
                        if (isInternetAvailable(this@MainActivity))
                            TopListScreen(moviesViewModel, navController)
                        else
                            ErrorScreen(navController = navController, errorSource = "listMovies")
                    }
                    composable("movie/{id}", arguments = listOf(navArgument("id") {
                        type = NavType.StringType
                    })) {
                        val id = it.arguments?.getString("id")?.toInt()
                        if (id != null) {
                            if (isInternetAvailable(this@MainActivity)) {
                                moviesViewModel.getMovie(id)
                                val movie = moviesViewModel.movieState.value
                                MovieScreen(
                                    movie,
                                    Modifier.padding(start = 30.dp, bottom = 10.dp, end = 30.dp)
                                )
                            } else
                                ErrorScreen(
                                    navController = navController,
                                    errorSource = "movie/$id"
                                )
                        }
                    }
                }
            }
        }
    }

    private fun isInternetAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val activeNetworkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}