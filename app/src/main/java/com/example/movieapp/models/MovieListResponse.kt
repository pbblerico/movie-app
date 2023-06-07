package com.example.movieapp.models

import com.google.gson.annotations.SerializedName


data class MovieListResponse(
//    @SerializedName("page")
//    val page: Int, // 1
    @SerializedName("results")
    val results: List<Movies>,
//    @SerializedName("total_pages")
//    val totalPages: Int, // 34218
//    @SerializedName("total_results")
//    val totalResults: Int // 684341
) {
    open class Movies(
        @SerializedName("adult")
        open val adult: Boolean = false, // false
        @SerializedName("backdrop_path")
        open val backdropPath: String = "wcKFYIiVDvRURrzglV9kGu7fpfY.jpg", // /wcKFYIiVDvRURrzglV9kGu7fpfY.jpg
        @SerializedName("genre_ids")
        open val genreIds: List<Int>,
        @SerializedName("id")
        open val id: Long = 453395, // 453395
        @SerializedName("original_language")
        open val originalLanguage: String = "en", // en
        @SerializedName("original_title")
        open val originalTitle: String = "Doctor Strange in the Multiverse of Madness", // Doctor Strange in the Multiverse of Madness
        @SerializedName("overview")
        open val overview: String, // Doctor Strange, with the help of mystical allies both old and new, traverses the mind-bending and dangerous alternate realities of the Multiverse to confront a mysterious new adversary.
        @SerializedName("popularity")
        open val popularity: Double, // 7931.499
        @SerializedName("poster_path")
        open val posterPath: String, // /9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg
        @SerializedName("release_date")
        open val releaseDate: String, // 2022-05-04
        @SerializedName("title")
        open val title: String, // Doctor Strange in the Multiverse of Madness
//        @SerializedName("video")
//        open val video: Boolean, // false
        @SerializedName("vote_average")
        open val voteAverage: Double, // 7.5
        @SerializedName("vote_count")
        open val voteCount: Long// 3987
    )
}