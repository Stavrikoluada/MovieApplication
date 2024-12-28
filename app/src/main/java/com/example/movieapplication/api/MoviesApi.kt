package com.example.movieapplication.api

import com.example.movieapplication.data.ActorListDto
import com.example.movieapplication.data.ResultGenresDto
import com.example.movieapplication.data.ResultMoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): ResultMoviesDto

    @GET("genre/movie/list")
    suspend fun getPopularMoviesGenres(
        @Query("api_key") apiKey: String
    ): ResultGenresDto

    @GET("movie/{id}/credits")
    suspend fun getActors(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
    ): ActorListDto
}
