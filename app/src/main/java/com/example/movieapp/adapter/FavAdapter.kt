package com.example.movieapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.movieapp.R
import com.example.movieapp.activity.MovieDetailActivity
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.utils.Constants.POSTER_BASE_URL
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavAdapter : RecyclerView.Adapter<FavAdapter.HolderFav> {
    private lateinit var binding: ItemViewBinding
    private var context: Context
    var movieArrayList: ArrayList<MovieListResponse.Movies>

    constructor(context: Context, movieArrayList: ArrayList<MovieListResponse.Movies>) {
        this.context = context
        this.movieArrayList = movieArrayList
    }

    inner class HolderFav(itemView: View): RecyclerView.ViewHolder(itemView) {
        var movieName: TextView = binding.tvMovieName
        var rate: TextView = binding.tvRate
        var lang: TextView = binding.tvLang
        var date: TextView = binding.tvMovieDateRelease
        var img: ShapeableImageView = binding.ImgMovie
        var removeFromFav: ImageView = binding.imgLike
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderFav {
        binding = ItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderFav(binding.root)
    }

    override fun getItemCount(): Int {
        return movieArrayList.size
    }

    override fun onBindViewHolder(holder: HolderFav, position: Int) {
        val model = movieArrayList[position]

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(FirebaseAuth.getInstance().uid!!).child("Liked").child(model.id.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val title = "${snapshot.child("title").value}"
                    val rating = "${snapshot.child("voteAverage").value}"
                    val language = "${snapshot.child("originalLanguage").value}"
                    val releaseDate = "${snapshot.child("releaseDate").value}"
                    val image = POSTER_BASE_URL + "${snapshot.child("posterPath").value}"

                    holder.movieName.text = title
                    holder.rate.text = rating
                    holder.lang.text = language
                    holder.date.text = releaseDate
                    holder.img.load(image){
                        crossfade(true)
                        placeholder(R.drawable.poster_placeholder)
                        scale(Scale.FILL)
                    }

                    holder.itemView.setOnClickListener {
                        val intent = Intent(context, MovieDetailActivity::class.java)
                        intent.putExtra("id", model.id)
                        context.startActivity(intent)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        holder.removeFromFav.setOnClickListener {
            delete(model, holder)
        }
    }

    fun delete(model: MovieListResponse.Movies, holder: FavAdapter.HolderFav) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
            .setMessage("Are you sure you want to remove this book from favourites?")
            .setPositiveButton("Confirm"){a, d ->
                Toast.makeText(context, "Deleting", Toast.LENGTH_SHORT).show()
                removeFromFav(model, holder)
            }
            .setNegativeButton("Cancel") {a, d ->
                a.dismiss()
            }.show()
    }

    private fun removeFromFav(model: MovieListResponse.Movies, holder: FavAdapter.HolderFav) {

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(FirebaseAuth.getInstance().uid!!).child("Liked").child(model.id.toString())
            .removeValue() .addOnSuccessListener {
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e ->
                Toast.makeText(context, "Unable to delete due to $e", Toast.LENGTH_SHORT).show()
            }
    }
}