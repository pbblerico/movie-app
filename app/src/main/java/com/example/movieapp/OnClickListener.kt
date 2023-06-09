package com.example.movieapp

import com.example.movieapp.models.Movie

interface OnClickListener {
    fun onItemClick(movie: Movie)
}