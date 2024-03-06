package ru.korobeynikov.topmovieskinopoisk.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.topmovieskinopoisk.data.repositories.MoviesRepositoryImpl
import ru.korobeynikov.topmovieskinopoisk.domain.MoviesRepository

@Module
interface RepositoriesModule {
    @Binds
    fun bindMoviesRepository(moviesRepositoryImpl:MoviesRepositoryImpl):MoviesRepository
}