package ru.korobeynikov.topmovieskinopoisk.data.webapi

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.korobeynikov.topmovieskinopoisk.data.network.MovieElementData
import ru.korobeynikov.topmovieskinopoisk.data.network.QueryMovies

interface KinopoiskAPI {

    @Headers("X-API-KEY: ca5545ad-57a0-49c8-9337-1cf3d1f87234")
    @GET("films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopMovies(): QueryMovies

    @Headers("X-API-KEY: ca5545ad-57a0-49c8-9337-1cf3d1f87234")
    @GET("films/{id}")
    suspend fun getMovie(@Path("id") id: Int): MovieElementData
}