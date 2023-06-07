package com.example.movieapp.retrofit.api

import com.example.movieapp.models.MovieDetails
import com.example.movieapp.models.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Long): Call<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Call<MovieListResponse>
}