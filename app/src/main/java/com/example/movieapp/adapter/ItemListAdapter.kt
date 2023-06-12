package com.example.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.models.ListItem

class ItemListAdapter: PagingDataAdapter<ListItem, ItemListViewHolder>(DiffCallback()) {
    class DiffCallback: DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem

    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val currentItem = getItem(position)!!
        holder.bind(currentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListViewHolder(binding)
    }
}