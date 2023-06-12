package com.example.movieapp.movieList.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.MockData
import com.example.movieapp.models.ListItem
//import com.example.movieapp.models.Movie
import com.example.movieapp.retrofit.api.ApiService

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
            val advert = MockData().data

            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            var nextKey = if (pageNumber < response.totalPages) pageNumber + 1 else null

//            var list: List<ListItem> = response.results.map { movie -> ListItem.Movie(movie) }
//            response.results.map { movie -> ListItem.Movie(movie) }
            val list = buildList<ListItem> {
                addAll(response.results.map { movie -> ListItem.Movie(movie) })
                add(ListItem.Ad(advert[(0..4).random()]))
            }
            Log.d("source", list.size.toString())
//            Log.d("source", ListItem.Ad(advert[(0..4).random()]).toString())
//            list.forEach {
//                Log.d("source", it.toString())
////                Log.d("Source", ListItem.Ad(advert[(0..4).random()]).toString())
//            }

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