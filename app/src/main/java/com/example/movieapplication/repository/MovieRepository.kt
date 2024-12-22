package com.example.movieapplication.repository

import android.util.Log
import com.example.movieapplication.api.MoviesApi
import com.example.movieapplication.data.MovieModel

class MovieRepository(private val movieApi: MoviesApi) {

    private var genresMap: Map<Int, String> = emptyMap()

    suspend fun updateGenresMap(apiKey: String) {
        try {
            val genresResponse = movieApi.getPopularMoviesGenres(apiKey)
            genresMap = genresResponse.resultsGenres.associate { it.id to it.name }
        } catch (e: Exception) {
            println("Error fetching genres: ${e.message}")
        }
    }

    suspend fun getPopularMovies(apiKey: String): List<MovieModel> {
        if (genresMap.isEmpty()) {
            updateGenresMap(apiKey)
        }

        // Получаем список популярных фильмов
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
                    poster = movieDto.posterPath,
                    backdrop = movieDto.backdropPath,
                    ratings = movieDto.rating,
                    ratingCount = movieDto.ratingCount,
                    minimumAge = movieDto.adult,
                    like = false,
                    genres = getGenreNamesByIds(movieDto.genreIDS, genresMap)
                )
            } ?: emptyList()
        }
    }


    fun getGenreNamesByIds(genreIDS: List<Long>, genresMap: Map<Int, String>): String {
        val genreNames = genreIDS.mapNotNull { id ->
            genresMap[id.toInt()]
        }
        return genreNames.joinToString(", ")
    }
}
