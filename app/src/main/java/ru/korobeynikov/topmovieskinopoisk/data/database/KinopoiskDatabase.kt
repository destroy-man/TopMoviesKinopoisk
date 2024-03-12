package ru.korobeynikov.topmovieskinopoisk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieTable::class], version = 1)
abstract class KinopoiskDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDAO
}