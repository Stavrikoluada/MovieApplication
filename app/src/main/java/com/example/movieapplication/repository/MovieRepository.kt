package com.example.movieapplication.repository

import com.example.movieapplication.data.MovieModel

class MovieRepository {
    fun getMovies(): List<MovieModel> {
        // Здесь вы можете выполнять запросы в базу данных или API.
        // Фейковый список:
        return listOf(
            MovieModel(
                "15",
                "Comedi",
                "One one",
                "100",
                "30",
                8
            ),
            MovieModel(
                "18",
                "Dramma",
                "My Home",
                "80",
                "20",
                4
            ),
            MovieModel(
                "1",
                "Merrrrgrgdg fgfdbd fbbdfb",
                "Aple fdkjdfhjvb dhfjgbhfdb fb",
                "20",
                "2000",
                1
            ),
        )
    }
}