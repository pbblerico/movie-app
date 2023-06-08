package com.example.movieapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.movieapp.R
import com.example.movieapp.activity.MovieDetailActivity
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.favouriteList.ui.FavouriteFragmentDirections
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.movieList.ui.MovieFragmentDirections
import com.example.movieapp.utils.Constants.POSTER_BASE_URL
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavAdapter : ListAdapter<Movie, FavAdapter.FavViewHolder>(DiffCallback()) {
   class FavViewHolder(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                tvMovieName.text = movie.title
                tvRate.text = movie.voteAverage.toString()
                tvLang.text = movie.originalLanguage
                tvMovieDateRelease.text = movie.releaseDate

                val image = POSTER_BASE_URL + movie.posterPath
                movieImg.load(image) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
            }
        }
   }

    class DiffCallback: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val action = FavouriteFragmentDirections.favToMovieDetailFragment(currentItem.id)
            Navigation.findNavController(it).navigate(action)
        }
    }
}