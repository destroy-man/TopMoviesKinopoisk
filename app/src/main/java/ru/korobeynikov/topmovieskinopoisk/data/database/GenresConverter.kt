package ru.korobeynikov.topmovieskinopoisk.data.database

import androidx.room.TypeConverter

class GenresConverter {

    @TypeConverter
    fun fromGenres(genres: List<String>) = genres.joinToString(",")

    @TypeConverter
    fun toGenres(data: String) = data.split(",").map { it.trim() }
}