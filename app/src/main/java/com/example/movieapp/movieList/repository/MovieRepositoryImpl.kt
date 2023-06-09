package com.example.movieapp.movieList.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.models.Movie
import com.example.movieapp.paging.MoviePagingSource
import com.example.movieapp.retrofit.api.ApiService
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(private val apiService: ApiService): MovieRepository {
    override suspend fun getMovieList(page: Int): Result<List<Movie>> = try {
        val result = apiService.getPopularMovie(page).results
        if (result.isEmpty()) Result.Empty()
        else Result.Success(result)
    } catch (e: Exception) {
        Result.Failure(e.message ?: e.localizedMessage)
    }

    override fun getPagedMovieList() = Pager(
            PagingConfig(1)
        ) {
            MoviePagingSource(apiService)
        }.flow

    override suspend fun addToFavourite(movie: Movie, result: (Result<String>) -> Unit) {
        withContext(Dispatchers.IO) {
            Constants.ref.getReference("Users")
                        .child(Constants.auth.uid!!)
                        .child("Liked")
                        .child(movie.id.toString())
                        .setValue(movie)
                        .addOnSuccessListener {
                            result.invoke(
                                Result.Success("added")
                            )
                        }.addOnFailureListener { e ->
                            result.invoke(
                                Result.Failure(e.message ?: e.localizedMessage)
                            )
                        }
        }
    }

}