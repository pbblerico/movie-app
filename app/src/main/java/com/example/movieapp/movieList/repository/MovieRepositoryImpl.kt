package com.example.movieapp.movieList.repository

import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.movieList.repository.MovieRepository
import com.example.movieapp.retrofit.api.ApiServiceTest
import com.example.movieapp.utils.Result

class MovieRepositoryImpl(private val apiService: ApiServiceTest): MovieRepository {
    override suspend fun getMovieList(page: Int): MovieListResponse = apiService.getPopularMovie(page)

    override suspend fun getMovieDetail(id: Long): Result<Movie> = apiService.getMovieDetails(id)

}