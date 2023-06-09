package com.example.movieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.favouriteList.repository.FavouritesRepository
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Result

class FavouritesPagingSource(private val favouriteRepository: FavouritesRepository): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: 1

            var response: Result<List<Movie>>? = null
            favouriteRepository.getFavouritesList {

            }
            TODO()
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}