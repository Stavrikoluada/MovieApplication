package com.example.movieapplication.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ActorListDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("cast")
    val cast: List<ActorDto>,
)

@Serializable
data class ActorDto(
    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("gender")
    val gender: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("profile_path")
    val profilePath: String? = null
)
