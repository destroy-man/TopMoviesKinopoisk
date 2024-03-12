package ru.korobeynikov.topmovieskinopoisk.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(GenresConverter::class)
data class MovieTable(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String,
    val genres: List<String>,
    val year: Int,
)