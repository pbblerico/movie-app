package com.example.movieapp.movieList.repository

import androidx.paging.PagingData
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(page: Int): Result<List<Movie>>

    suspend fun getMovieListPaging(): Flow<PagingData<Movie>>

}