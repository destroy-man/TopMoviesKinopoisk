package ru.korobeynikov.topmovieskinopoisk.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.topmovieskinopoisk.data.repositories.DatabaseRepositoryImpl
import ru.korobeynikov.topmovieskinopoisk.data.repositories.NetworkRepositoryImpl
import ru.korobeynikov.topmovieskinopoisk.domain.DatabaseRepository
import ru.korobeynikov.topmovieskinopoisk.domain.NetworkRepository

@Module
interface RepositoriesModule {
    @Binds
    fun bindNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    fun bindDatabaseRepository(databaseRepositoryImpl: DatabaseRepositoryImpl): DatabaseRepository
}