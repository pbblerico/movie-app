package com.example.movieapp.movieList.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(page: Int = 1): Result<List<Movie>>

    fun getMoviePagingSource(): MoviePagingSource

    suspend fun addToFavourite(movie: Movie, result: (Result<String>) -> Unit)
}