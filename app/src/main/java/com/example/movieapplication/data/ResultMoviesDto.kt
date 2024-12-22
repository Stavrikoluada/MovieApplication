package com.example.movieapplication.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResultMoviesDto(
    val dates: DatesDto,
    val page: Int,
    val results: List<MoviesDto>
)

@Serializable
data class DatesDto(
    val maximum: String,
    val minimum: String
)

@Serializable
data class MoviesDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("genre_ids")
    val genreIDS: List<Long>,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val rating: Float,

    @SerializedName("vote_count")
    val ratingCount: Long,
)
