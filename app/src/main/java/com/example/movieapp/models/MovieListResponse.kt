package com.example.movieapp.models

import com.google.gson.annotations.SerializedName


data class MovieListResponse(
    @SerializedName("results")
    val results: List<Movie>
)