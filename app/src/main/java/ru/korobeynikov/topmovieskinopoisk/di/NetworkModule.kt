package ru.korobeynikov.topmovieskinopoisk.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.korobeynikov.topmovieskinopoisk.data.repositories.NetworkRepositoryImpl

@Module
class NetworkModule {

    @Provides
    fun provideNetworkRepositoryImpl(retrofit: Retrofit) = NetworkRepositoryImpl(retrofit)

    @ApplicationScope
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
}