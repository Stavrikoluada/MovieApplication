package com.example.movieapplication.repository

import android.util.Log
import com.example.movieapplication.api.MoviesApi
import com.example.movieapplication.data.ActorsModel
import com.example.movieapplication.data.MovieModel

class MovieRepository(private val movieApi: MoviesApi) {

    private var genresMap: MutableMap<Int, String> = mutableMapOf(28 to "Action")

    suspend fun updateGenresMap(apiKey: String) {
        try {
            val genresResponse = movieApi.getPopularMoviesGenres(apiKey)
            genresMap = genresResponse.genres.associate { it.id to it.name }.toMutableMap()
        } catch (e: Exception) {
            println("Error fetching genres: ${e.message}")
        }
    }

    suspend fun getActorsForId(id: Long, apiKey: String): List<ActorsModel>? {
        val response = movieApi.getActors(id, apiKey)

        if (response.cast == null) {
            Log.e("MovieRepository", "No actors found")
            return emptyList()
        } else {
            return response.cast?.map { actorDto ->
                ActorsModel(
                    name = actorDto.name,
                    profilePath = actorDto.profilePath
                )
            }
        }
    }

    suspend fun getPopularMovies(apiKey: String): List<MovieModel> {
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
