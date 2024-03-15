package ru.korobeynikov.topmovieskinopoisk.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.korobeynikov.topmovieskinopoisk.ui.theme.blue

@Composable
fun ProgressScreen() = Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
    CircularProgressIndicator(color = blue)
}