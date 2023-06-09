package com.example.movieapp

import com.example.movieapp.models.Movie

interface MovieAdapterClickListener {
    fun onItemClick(movie: Movie)
    fun onLikeButtonClick(movie: Movie)
}