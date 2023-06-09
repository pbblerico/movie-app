package com.example.movieapp.favouriteList.repository

import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Result

interface FavouritesRepository {
    suspend fun getFavouritesList(result: (Result<List<Movie>>) -> Unit)

    suspend fun removeFromFavourite(id: String, result: (Result<String>) -> Unit)

}