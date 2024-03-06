package ru.korobeynikov.topmovieskinopoisk.di

import android.app.Application

class App:Application() {

    lateinit var moviesComponent: MoviesComponent

    override fun onCreate() {
        super.onCreate()
        moviesComponent=DaggerMoviesComponent.create()
    }
}