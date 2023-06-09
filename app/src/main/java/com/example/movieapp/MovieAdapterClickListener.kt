package com.example.movieapp

import com.example.movieapp.models.Movie

interface MovieAdapterClickListener: OnClickListener {
    fun onLikeButtonClick(movie: Movie)
}