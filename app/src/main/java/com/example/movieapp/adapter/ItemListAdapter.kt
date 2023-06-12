package com.example.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.R
import com.example.movieapp.databinding.AdvertViewBinding
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.models.ListItem
import com.example.movieapp.models.Movie

class ItemListAdapter(var onItemClicked: ((movie: Movie) -> Unit), var onLikeButtonClicked: (movie: Movie) -> Unit): PagingDataAdapter<ListItem, ItemListViewHolder>(DiffCallback()) {
    class DiffCallback: DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem

    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is ListItem.Movie -> TYPE_MOVIE
            else -> TYPE_ADVERT
        }
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val currentItem = getItem(position)!!
        holder.bind(currentItem)

        when(currentItem) {
            is ListItem.Movie -> onMovieBindVH(holder, currentItem.movie)
            else -> onAdvertBindVH(holder, currentItem)
        }
    }
    private fun onAdvertBindVH(holder: ItemListViewHolder, currentItem: ListItem) {
//        (holder.binding as AdvertViewBinding)
    }

    private fun onMovieBindVH(holder: ItemListViewHolder, movie: Movie) {
        holder.itemView.setOnClickListener {
            onItemClicked.invoke(movie)
        }

        (holder.binding as ItemViewBinding).imgLike.setOnClickListener {
            onLikeButtonClicked.invoke(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val binding = when(viewType) {
            TYPE_MOVIE -> ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            else -> AdvertViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }

        return ItemListViewHolder(binding)
    }

    companion object{
        private const val TYPE_MOVIE = 0
        private const val TYPE_ADVERT = 1
    }
}