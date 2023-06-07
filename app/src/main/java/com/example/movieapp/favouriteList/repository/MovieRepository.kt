package com.example.movieapp.favouriteList.repository

import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.utils.Result

interface MovieRepository {
    suspend fun getMovieList(): Result<MovieListResponse>

//    suspend fun getMovieDetail()
}