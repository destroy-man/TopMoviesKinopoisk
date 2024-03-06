package ru.korobeynikov.topmovieskinopoisk.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.korobeynikov.topmovieskinopoisk.data.repositories.MoviesRepositoryImpl
import ru.korobeynikov.topmovieskinopoisk.domain.MoviesRepository
import ru.korobeynikov.topmovieskinopoisk.presentation.MoviesViewModelFactory

@Module
class MoviesViewModelModule {

    @Provides
    fun provideMoviesRepositoryImpl(retrofit: Retrofit) = MoviesRepositoryImpl(retrofit)

    @Provides
    fun provideRetrofit() =
        Retrofit.Builder().baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
}