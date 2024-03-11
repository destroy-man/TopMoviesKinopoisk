package ru.korobeynikov.topmovieskinopoisk.presentation.toplistmovies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun TopListScreen(listMovies: List<MovieListElement>, navController: NavHostController) =
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(listMovies.size) {
            Movie(movie = listMovies[it], navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

@Composable
fun Movie(
    movie: MovieListElement,
    navController: NavHostController,
) = Row(
    modifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(20.dp))
        .fillMaxWidth()
        .padding(10.dp)
        .clickable {
            navController.navigate("movie/${movie.id}")
        }
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