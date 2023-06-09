package com.example.movieapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.models.Ad
import com.example.movieapp.models.ListItem
import com.example.movieapp.models.Movie
import com.example.movieapp.movieList.repository.MovieRepository
import com.example.movieapp.retrofit.api.ApiService
import com.example.movieapp.utils.Result
class MoviePagingSource(private val apiService: ApiService): PagingSource<Int, ListItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        return try {
            val pageNumber = params.key ?: 1

            val response = apiService.getPopularMovie(pageNumber)


            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            var nextKey = if (pageNumber < response.totalPages) pageNumber + 1 else null

            val list = buildList<ListItem> {
                addAll(response.results)
                add(ListItem.Ad("1Xbet", "Delai stavki", ""))
            }
            LoadResult.Page(
                data = list,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}