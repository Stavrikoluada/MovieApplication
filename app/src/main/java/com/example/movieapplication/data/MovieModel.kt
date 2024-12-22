package com.example.movieapplication.data

data class MovieModel(
    val id: Long,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val ratings: Float,
    val ratingCount: Long,
    val minimumAge: Int,
    val like: Boolean,
    val genres: String,
)
