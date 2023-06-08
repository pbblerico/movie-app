package com.example.movieapp.retrofit.api

import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.movieapp.utils.Result


interface ApiServiceTest {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Long): Result<Movie>

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int): MovieListResponse
}