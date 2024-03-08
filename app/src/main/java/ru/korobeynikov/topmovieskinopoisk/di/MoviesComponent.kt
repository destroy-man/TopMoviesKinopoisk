package ru.korobeynikov.topmovieskinopoisk.di

import dagger.Component
import ru.korobeynikov.topmovieskinopoisk.presentation.MainActivity

@ApplicationScope
@Component(modules = [MoviesViewModelModule::class, RepositoriesModule::class])
interface MoviesComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}