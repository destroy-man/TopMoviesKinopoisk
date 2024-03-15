package ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import ru.korobeynikov.topmovieskinopoisk.R
import ru.korobeynikov.topmovieskinopoisk.presentation.ErrorScreen
import ru.korobeynikov.topmovieskinopoisk.presentation.viewmodels.MoviesViewModel
import ru.korobeynikov.topmovieskinopoisk.ui.theme.blue

@Composable
fun TopListScreenPopular(
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
    onAddMovie: (movie: MovieListElement) -> Unit,
) {
    val listMovies by moviesViewModel.topMoviesState
    val listSavedMovies by moviesViewModel.savedMoviesState
    Scaffold(topBar = {
        TopBarList(moviesViewModel, true)
    }, bottomBar = {
        BottomBarList(navController, true)
    }) { innerPading ->
        val isError by moviesViewModel.topMoviesErrorState
        if (isError) {
            if (listMovies.isNotEmpty())
                ListMovies(innerPading, listMovies, navController, onAddMovie)
            else if (listSavedMovies.isNotEmpty())
                ListMovies(innerPading, listSavedMovies, navController, onAddMovie)
            else
                ErrorScreen {
                    navController.navigate("listMovies/popular")
                }
        } else
            ListMovies(innerPading, listMovies, navController, onAddMovie)
    }
}

@Composable
fun TopListScreenSaved(
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
    onAddMovie: (movie: MovieListElement) -> Unit,
) {
    val listMovies by moviesViewModel.savedMoviesState
    Scaffold(topBar = {
        TopBarList(moviesViewModel, false)
    }, bottomBar = {
        BottomBarList(navController, false)
    }) { innerPading ->
        ListMovies(innerPading, listMovies = listMovies, navController = navController, onAddMovie)
    }
}

@Composable
fun ListMovies(
    innerPading: PaddingValues,
    listMovies: List<MovieListElement>,
    navController: NavHostController,
    onAddMovie: (movie: MovieListElement) -> Unit,
) {
    Column(modifier = Modifier.padding(innerPading)) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(listMovies.size) {
                Movie(listMovies[it], navController, onAddMovie)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarList(moviesViewModel: MoviesViewModel, isPopularList: Boolean) {
    var textSearch by remember {
        mutableStateOf("")
    }
    TopAppBar(title = {
        SearchBar(
            placeholder = {
                Text(text = "Поиск")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = null,
                    tint = blue,
                )
            },
            query = textSearch,
            onQueryChange = {
                textSearch = it
                if (isPopularList) {
                    val listMovies = moviesViewModel.listTopMovies.filter { movie ->
                        movie.name.contains(textSearch, ignoreCase = true)
                    }
                    moviesViewModel.loadTopMoviesState(listMovies)
                } else {
                    val listMovies = moviesViewModel.listSavedMovies.filter { movie ->
                        movie.name.contains(textSearch, ignoreCase = true)
                    }
                    moviesViewModel.loadSavedMoviesState(listMovies)
                }
            },
            onSearch = {},
            active = false,
            onActiveChange = {}
        ) {}
    })
}

@Composable
fun BottomBarList(navController: NavHostController, isPopularList: Boolean) {
    BottomAppBar(containerColor = Color.White) {
        Row {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                onClick = {
                    navController.navigate("listMovies/popular")
                },
                colors = ButtonDefaults.buttonColors(containerColor = blue),
                enabled = !isPopularList
            ) {
                Text(text = "Популярное")
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                onClick = {
                    navController.navigate("listMovies/saved")
                },
                colors = ButtonDefaults.buttonColors(containerColor = blue),
                enabled = isPopularList
            ) {
                Text(text = "Избранное")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Movie(
    movie: MovieListElement,
    navController: NavHostController,
    onAddMovie: (movie: MovieListElement) -> Unit,
) = Row(
    modifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(20.dp))
        .fillMaxWidth()
        .padding(10.dp)
        .combinedClickable(
            onClick = {
                navController.navigate("movie/${movie.id}")
            }, onLongClick = {
                onAddMovie(movie)
            }
        )
) {
    AsyncImage(
        model = movie.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(60.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
    )
    Spacer(modifier = Modifier.width(20.dp))
    val genres = movie.genres.toString().replace("[", "").replace("]", "")
    Column {
        Row {
            Text(
                text = movie.name,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 20.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            if (movie.isSaved)
                Icon(
                    painter = painterResource(id = R.drawable.saved_icon),
                    contentDescription = null,
                    tint = blue,
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
                )
        }
        Text(text = "$genres (${movie.year})", modifier = Modifier.padding(top = 20.dp))
    }
}