package com.example.movieapp.movieList.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.models.Movie
import com.example.movieapp.paging.MoviePagingSource
import com.example.movieapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(page: Int = 1): Result<List<Movie>>

    fun getPagedMovieList(): Flow<PagingData<Movie>>

    suspend fun addToFavourite(movie: Movie, result: (Result<String>) -> Unit)
}