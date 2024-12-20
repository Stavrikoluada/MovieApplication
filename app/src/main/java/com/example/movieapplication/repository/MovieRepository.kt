package com.example.movieapplication.repository

import android.util.Log
import com.example.movieapplication.api.MoviesApi
import com.example.movieapplication.data.MovieModel

class MovieRepository(private val movieApi: MoviesApi) {

    suspend fun getPopularMovies(apiKey: String): List<MovieModel> {
        // Получаем популярные фильмы с использованием Retrofit
        val response = movieApi.getPopularMovies(apiKey)
        if (response.results == null) {
            Log.e("MovieRepository", "No movies found")
            return emptyList()
        } else {
            return response.results?.map { movieDto ->
                MovieModel(
                    id = movieDto.id,
                    title = movieDto.title,
                    overview = movieDto.overview,
                    poster = "https://image.tmdb.org/t/p/w342${movieDto.posterPath}",
                    backdrop = movieDto.backdropPath ?: "",
                    ratings = movieDto.rating,
                    numberOfRatings = movieDto.ratingCount,
                    minimumAge = if (movieDto.adult) 16 else 13,
                    like = false,
                    genres = movieDto.genreIDS?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "No genres"
                )
            } ?: emptyList()
        }
    }
}