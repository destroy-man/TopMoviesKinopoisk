package ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
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
import ru.korobeynikov.topmovieskinopoisk.ui.theme.blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopListScreen(listMovies: List<MovieListElement>, navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SearchBar(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = null,
                            tint = blue,
                        )
                    },
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    active = false,
                    onActiveChange = {}
                ) {

                }
            }
        })
    }, bottomBar = {
        BottomAppBar(containerColor = Color.White) {
            Row {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = blue),
                    enabled = false
                ) {
                    Text(text = "Популярное")
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = blue),
                    enabled = true
                ) {
                    Text(text = "Избранное")
                }
            }
        }
    }) { innerPading ->
        Column(modifier = Modifier.padding(innerPading)) {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(listMovies.size) {
                    Movie(movie = listMovies[it], navController)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Movie(
    movie: MovieListElement,
    navController: NavHostController,
) = Row(
    modifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(20.dp))
        .fillMaxWidth()
        .padding(10.dp)
        .combinedClickable(
            onClick = {
                navController.navigate("movie/${movie.id}")
            }, onLongClick = {

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
        Text(
            text = movie.name,
            modifier = Modifier.padding(top = 20.dp),
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(text = "$genres (${movie.year})", modifier = Modifier.padding(top = 20.dp))
    }
}