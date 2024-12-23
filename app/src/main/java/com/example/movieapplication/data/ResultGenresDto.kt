package com.example.movieapplication.data
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResultGenresDto(
    val genres: List<GenresDto>
)
@Serializable
data class GenresDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,
)

