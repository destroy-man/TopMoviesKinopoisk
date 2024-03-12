package ru.korobeynikov.topmovieskinopoisk.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.korobeynikov.topmovieskinopoisk.data.database.KinopoiskDatabase
import ru.korobeynikov.topmovieskinopoisk.data.repositories.DatabaseRepositoryImpl

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    fun provideDatabaseRepositoryImpl(kinopoiskDatabase: KinopoiskDatabase) =
        DatabaseRepositoryImpl(kinopoiskDatabase)

    @ApplicationScope
    @Provides
    fun provideKinopoiskDatabase() =
        Room.databaseBuilder(context, KinopoiskDatabase::class.java, "Kinopoisk").build()
}