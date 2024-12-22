package com.example.movieapplication.api

import com.example.movieapplication.data.ResultGenresDto
import com.example.movieapplication.data.ResultMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): ResultMoviesDto

    @GET("genre/movie/list")
    suspend fun getPopularMoviesGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en"
    ): ResultGenresDto
}