package com.example.movieapp.movieList.repository

import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.utils.Result

interface MovieRepository {
    suspend fun getMovieList(page: Int): Result<List<Movie>>



}