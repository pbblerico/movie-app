package com.example.movieapp.movieList.repository

import com.example.movieapp.models.Movie
import com.example.movieapp.retrofit.api.ApiService
import com.example.movieapp.utils.Result

class MovieRepositoryImpl(private val apiService: ApiService): MovieRepository {
    override suspend fun getMovieList(page: Int): Result<List<Movie>> = try {
        val result = apiService.getPopularMovie(page).results
        if (result.isEmpty()) Result.Empty()
        else Result.Success(result)
    } catch (e: Exception) {
        Result.Failure(e.message ?: e.localizedMessage)
    }



}