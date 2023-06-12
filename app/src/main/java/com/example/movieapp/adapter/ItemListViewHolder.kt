package com.example.movieapp.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.size.Scale
import com.example.movieapp.R
import com.example.movieapp.databinding.AdvertViewBinding
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.models.Advert
import com.example.movieapp.models.ListItem
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants

class ItemListViewHolder(val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListItem) {
        when(item) {
            is ListItem.Movie ->  bindMovies(item.movie)
            is ListItem.Ad -> bindAd(item.ad)
            else -> null
        }
    }

    private fun bindMovies(movie: Movie) {
        (binding as ItemViewBinding).apply {
                tvMovieName.text = movie.title
                tvRate.text = movie.voteAverage.toString()
                tvLang.text = movie.originalLanguage
                tvMovieDateRelease.text = movie.releaseDate

                val image = Constants.POSTER_BASE_URL + movie.posterPath
                movieImg.load(image) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
            }
    }
    private fun bindAd(ad: Advert) {
        (binding as AdvertViewBinding).apply {
            advTitle.text = ad.title
            advText.text = ad.description
            advImg.load(ad.bannerImage) {
                crossfade(true)
                scale(Scale.FILL)
            }
        }
    }
}