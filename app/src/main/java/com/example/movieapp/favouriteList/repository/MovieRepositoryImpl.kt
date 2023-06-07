package com.example.movieapp.favouriteList.repository

import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.utils.Result

class MovieRepositoryImpl: MovieRepository {
    override suspend fun getMovieList(): Result<MovieListResponse> {
        TODO("Not yet implemented")
    }
}