package ru.korobeynikov.topmovieskinopoisk.presentation

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun TopListScreen(moviesViewModel: MoviesViewModel= viewModel()){
    val listMovies by moviesViewModel.topMoviesState
    moviesViewModel.getTopMovies()
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(listMovies.size){
            Movie(movie = listMovies[it])
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun Movie(movie: MovieListElement){
    Row(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .padding(10.dp)
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
        val genres=movie.genres.toString().replace("[","").replace("]","")
        Column {
            Text(text = movie.name, modifier = Modifier.padding(top = 20.dp))
            Text(text = "$genres (${movie.year})", modifier = Modifier.padding(top = 20.dp))
        }
    }
}