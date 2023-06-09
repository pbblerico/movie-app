package com.example.movieapp

interface FavouriteAdapterClickListener: OnClickListener {
    fun removeFromFavourite(id: String)
}