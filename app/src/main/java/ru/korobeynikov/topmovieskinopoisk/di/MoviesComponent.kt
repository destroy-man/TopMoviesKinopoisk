package ru.korobeynikov.topmovieskinopoisk.di

import dagger.Component
import ru.korobeynikov.topmovieskinopoisk.presentation.MainActivity

@ApplicationScope
@Component(modules = [NetworkModule::class, RepositoriesModule::class, DatabaseModule::class])
interface MoviesComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}