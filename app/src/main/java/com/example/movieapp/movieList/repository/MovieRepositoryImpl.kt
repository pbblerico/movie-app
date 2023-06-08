package com.example.movieapp.movieList.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.movieapp.models.Movie
import com.example.movieapp.paging.PagingSourceImpl
import com.example.movieapp.retrofit.api.ApiService
import com.example.movieapp.utils.Result
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(private val apiService: ApiService): MovieRepository {
    override suspend fun getMovieList(page: Int): Result<List<Movie>> = try {
        val result = apiService.getPopularMovie(page).results
        if (result.isEmpty()) Result.Empty()
        else Result.Success(result)
    } catch (e: Exception) {
        Result.Failure(e.message ?: e.localizedMessage)
    }

    override suspend fun getMovieListPaging(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize = 20)
        ){
            PagingSourceImpl(apiService)
        }.flow
    }
}