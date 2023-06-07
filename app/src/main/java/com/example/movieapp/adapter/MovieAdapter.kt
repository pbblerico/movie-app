package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.movieapp.R
import com.example.movieapp.activity.MovieDetailActivity
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.retrofit.Constants.POSTER_BASE_URL
import com.example.movieapp.models.MovieListResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MovieAdapter: RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var binding: ItemViewBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemViewBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = differ.currentList[position]

        holder.bind(differ.currentList[position])

        holder.likeBtn.setOnClickListener { addToFav(model) }
    }

    private fun addToFav(model: MovieListResponse.Result) {
        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(FirebaseAuth.getInstance().uid!!).child("Liked").child(model.id.toString())
            .setValue(model)
            .addOnSuccessListener {
                Toast.makeText(context, "Added to favourite", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {e->
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        var likeBtn: ImageView = binding.imgLike

        @SuppressLint("SetTextI18n")
        fun bind(item: MovieListResponse.Result) {
            binding.apply {
                tvMovieName.text = item.title
                tvMovieDateRelease.text = item.releaseDate
                tvRate.text=item.voteAverage.toString()
                val moviePosterURL = POSTER_BASE_URL + item?.posterPath
                ImgMovie.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvLang.text=item.originalLanguage

                root.setOnClickListener {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra("id", item?.id)
                    context.startActivity(intent)
                }
            }

        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MovieListResponse.Result>() {
        override fun areItemsTheSame(oldItem: MovieListResponse.Result, newItem: MovieListResponse.Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListResponse.Result, newItem: MovieListResponse.Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}