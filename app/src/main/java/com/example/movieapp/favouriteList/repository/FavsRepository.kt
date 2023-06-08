package com.example.movieapp.favouriteList.repository

import com.example.movieapp.models.Movie

interface FavsRepository {
    suspend fun getFavsList(): Result<List<Movie>>

}