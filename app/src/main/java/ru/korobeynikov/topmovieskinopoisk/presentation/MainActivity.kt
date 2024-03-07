package ru.korobeynikov.topmovieskinopoisk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.korobeynikov.topmovieskinopoisk.di.App
import ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies.TopListScreen
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).moviesComponent.injectMainActivity(this)
        setContent {
            val moviesViewModel by viewModels<MoviesViewModel>{
                moviesViewModelFactory
            }
            Column(modifier = Modifier.fillMaxSize()) {
                val navController= rememberNavController()
                NavHost(navController = navController, startDestination = "listMovies", modifier = Modifier.weight(1f)){
                    composable("listMovies"){
                        TopListScreen(moviesViewModel, navController)
                    }
                    composable("movie/{id}", arguments = listOf(navArgument("id"){
                        type= NavType.StringType
                    })){
                        //840152
                        val id=it.arguments?.getString("id")
                        val movie = MovieElement(
                            "https://kinopoiskapiunofficial.tech/images/posters/kp/840152.jpg",
                            "Изгой-один: Звёздные войны. Истории",
                            "Сопротивление собирает отряд для выполнения особой миссии - " +
                                    "надо выкрасть чертежи самого совершенного и смертоносного оружия Империи. " +
                                    "Возглавляет бойцов неуправляемая и бесстрашная Джин Эрсо, у которой " +
                                    "в этом самоубийственном задании есть и личные мотивы. Не всем суждено " +
                                    "вернуться домой, но герои готовы к этому, ведь на кону судьба Галактики.",
                            listOf("фантастика","приключения","боевик"),
                            listOf("США")
                        )
                        MovieScreen(movie, Modifier.padding(start = 30.dp, bottom = 10.dp, end = 30.dp))
                    }
                }
            }
        }
    }
}