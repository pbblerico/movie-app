package com.example.movieapp.models

sealed interface ListItem {
    data class Movie(val movie: com.example.movieapp.models.Movie) : ListItem

    data class Ad(val ad: Advert) : ListItem
}