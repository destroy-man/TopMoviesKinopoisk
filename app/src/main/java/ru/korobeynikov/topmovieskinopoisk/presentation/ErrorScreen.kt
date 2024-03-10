package ru.korobeynikov.topmovieskinopoisk.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.korobeynikov.topmovieskinopoisk.R

@Composable
fun ErrorScreen(navController: NavHostController, errorSource: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val color = Color(0xFF0094FF)
        Icon(
            painter = painterResource(id = R.drawable.error_icon),
            contentDescription = null,
            tint = color
        )
        Text(
            text = "Произошла ошибка при загрузке данных, проверьте подключение к сети",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp),
            color = color
        )
        Button(onClick = {
            navController.navigate(errorSource)
        }, colors = ButtonDefaults.buttonColors(containerColor = color)) {
            Text(text = "Повторить")
        }
    }
}