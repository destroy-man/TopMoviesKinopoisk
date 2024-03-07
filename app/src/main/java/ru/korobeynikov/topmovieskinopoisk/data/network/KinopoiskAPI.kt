package ru.korobeynikov.topmovieskinopoisk.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KinopoiskAPI {
    @Headers("X-API-KEY: ca5545ad-57a0-49c8-9337-1cf3d1f87234")
    @GET("films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopMovies(): QueryMovies

//    @Headers("X-API-KEY: ca5545ad-57a0-49c8-9337-1cf3d1f87234")
//    @GET("films/4540126")
//    suspend fun getMovie(): String
}