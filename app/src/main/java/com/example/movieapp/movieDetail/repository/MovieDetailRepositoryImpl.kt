package com.example.movieapp.movieDetail.repository

import com.example.movieapp.models.Movie
import com.example.movieapp.retrofit.api.ApiService
import com.example.movieapp.utils.Result

class MovieDetailRepositoryImpl(private val apiService: ApiService): MovieDetailRepository {
    override suspend fun getMovieDetail(id: Long): Result<Movie> = try {
        val result = apiService.getMovieDetails(id)
        Result.Success(result)
    } catch (e: Exception) {
        Result.Failure(e.message ?: e.localizedMessage)
    }
}