package com.example.movieapplication.data
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResultGenresDto(
    val resultsGenres: List<GenresDto>
)
@Serializable
data class GenresDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,
)

