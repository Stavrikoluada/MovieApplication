package com.example.movieapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "poster")
    val poster: String?,

    @ColumnInfo(name = "backdrop")
    val backdrop: String?,

    @ColumnInfo(name = "ratings")
    val ratings: Float,

    @ColumnInfo(name = "rating_count")
    val ratingCount: Long,

    @ColumnInfo(name = "minimum_age")
    val minimumAge: Boolean,

    @ColumnInfo(name = "like")
    val like: Boolean,

    @ColumnInfo(name = "genres")
    val genres: String
)
