package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

sealed interface ListItem {
    data class Movie(
        @SerializedName("backdrop_path")
        open val backdropPath: String =
            "wcKFYIiVDvRURrzglV9kGu7fpfY.jpg", // /wcKFYIiVDvRURrzglV9kGu7fpfY.jpg
        @SerializedName("id")
        open val id: Long = 453395, // 453395
        @SerializedName("original_language")
        open val originalLanguage: String = "en", // en
        @SerializedName("overview")
        open val overview: String =
            "", // Doctor Strange, with the help of mystical allies both old and new, traverses the mind-bending and dangerous alternate realities of the Multiverse to confront a mysterious new adversary.
        @SerializedName("poster_path")
        val posterPath: String = "", // /9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg
        @SerializedName("release_date")
        val releaseDate: String = "", // 2022-05-04
        @SerializedName("title")
        open val title: String = "", // Doctor Strange in the Multiverse of Madness
        @SerializedName("vote_average")
        open val voteAverage: Double = 0.0, // 7.5
    ) : ListItem

    data class Ad(
        val title: String,
        val description: String,
        val bannerImage: String
    ) : ListItem
}