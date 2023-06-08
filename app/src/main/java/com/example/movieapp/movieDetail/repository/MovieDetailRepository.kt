package com.example.movieapp.movieDetail.repository

import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Result

interface MovieDetailRepository {
    suspend fun getMovieDetail(id: Long): Result<Movie>
}