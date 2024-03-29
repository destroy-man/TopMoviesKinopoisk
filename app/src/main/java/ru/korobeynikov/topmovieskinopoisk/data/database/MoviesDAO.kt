package ru.korobeynikov.topmovieskinopoisk.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM movietable")
    suspend fun getMovies(): List<MovieTable>

    @Query("SELECT * FROM movietable WHERE :id = id")
    suspend fun getMovie(id: Int): MovieTable?

    @Insert
    suspend fun addMovie(movie: MovieTable)

    @Delete
    suspend fun deleteMovie(movie: MovieTable)
}