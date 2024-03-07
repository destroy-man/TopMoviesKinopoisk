package ru.korobeynikov.topmovieskinopoisk.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun MovieScreen(movie:MovieElement, modifier: Modifier){
    Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
        AsyncImage(
            model = movie.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(bottom = 10.dp)
        )
        Text(
            text = movie.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier,
        )
        Text(text = movie.description, modifier = modifier)
        Row(modifier = modifier){
            val genres=movie.genres.toString().replace("[","").replace("]","")
            Text(text = "Жанры: ", fontWeight = FontWeight.Bold)
            Text(text = genres)
        }
        Row(modifier = modifier) {
            val countries=movie.countries.toString().replace("[","").replace("]","")
            Text(text = "Страны: ", fontWeight = FontWeight.Bold)
            Text(text = countries)
        }
    }
}