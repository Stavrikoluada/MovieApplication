package com.example.movieapplication.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultMoviesDto(
    val results: List<MoviesDto>?
)

@Serializable
data class MoviesDto(
    val id: Long,
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("genre_ids")
    val genreIDS: List<Long>,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String?,

    val title: String,
    val video: Boolean,

    @SerialName("vote_average")
    val rating: Float,

    @SerialName("vote_count")
    val ratingCount: Long,
)
