package ru.korobeynikov.topmovieskinopoisk.data.webapi

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.korobeynikov.topmovieskinopoisk.data.network.MovieElementData
import ru.korobeynikov.topmovieskinopoisk.data.network.QueryMovies

interface KinopoiskAPI {

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopMovies(): QueryMovies

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("films/{id}")
    suspend fun getMovie(@Path("id") id: Int): MovieElementData
}