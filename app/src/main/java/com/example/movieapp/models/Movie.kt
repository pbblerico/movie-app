package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("backdrop_path")
        val backdropPath: String =
            "wcKFYIiVDvRURrzglV9kGu7fpfY.jpg", // /wcKFYIiVDvRURrzglV9kGu7fpfY.jpg
        @SerializedName("id")
        val id: Long = 453395, // 453395
        @SerializedName("original_language")
        val originalLanguage: String = "en", // en
        @SerializedName("overview")
        val overview: String =
            "", // Doctor Strange, with the help of mystical allies both old and new, traverses the mind-bending and dangerous alternate realities of the Multiverse to confront a mysterious new adversary.
        @SerializedName("poster_path")
        val posterPath: String = "", // /9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg
        @SerializedName("release_date")
        val releaseDate: String = "", // 2022-05-04
        @SerializedName("title")
        val title: String = "", // Doctor Strange in the Multiverse of Madness
        @SerializedName("vote_average")
        val voteAverage: Double = 0.0, // 7.5
    ) : ListItem
