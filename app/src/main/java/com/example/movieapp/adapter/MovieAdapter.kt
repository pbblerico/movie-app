package com.example.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.movieList.ui.MovieFragmentDirections
import com.example.movieapp.utils.Constants.POSTER_BASE_URL

class MovieAdapter: ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback()) {
    class MovieViewHolder(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            Log.d("ADP", "works")
            val action = MovieFragmentDirections.toMovieDetailFragment(currentItem.id)
            Navigation.findNavController(it).navigate(action)
        }
    }
}