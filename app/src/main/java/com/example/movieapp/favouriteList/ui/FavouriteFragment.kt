package com.example.movieapp.favouriteList.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.adapter.FavAdapter
import com.example.movieapp.databinding.FragmentFavouriteBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieListResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FavouriteFragment : Fragment(R.layout.fragment_favourite) {
   private var binding: FragmentFavouriteBinding? = null

   private val TAG = "FAV"

   private lateinit var favArrayList: ArrayList<MovieListResponse.Movies>
   private lateinit var adapterFav: FavAdapter
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      binding = FragmentFavouriteBinding.inflate(inflater, container, false)


      loadFavs()
      return binding!!.root
   }

   private fun loadFavs() {
      favArrayList = ArrayList()

      val ref = FirebaseDatabase.getInstance().getReference("Users")
      ref.child(FirebaseAuth.getInstance().uid!!).child("Liked")
         .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//               binding.prgBarMovies.visibility = View.GONE
               favArrayList.clear()
//               Log.d(TAG, "${snapshot.value}")
               for (ds in snapshot.children) {
                  val adult = ds.child("adult").value as Boolean // false
                  val backdropPath = ds.child("backdropPath").value.toString()
                  val genreIds = ds.child("genreIds").value as List<Int>
                  val id = ds.child("id").value as Long
                  val originalLanguage = ds.child("originalLanguage").value.toString()
                  val originalTitle= ds.child("originalTitle").value.toString()
                  val overview = ds.child("overview").value.toString() // Doctor Strange, with the help of mystical allies both old and new, traverses the mind-bending and dangerous alternate realities of the Multiverse to confront a mysterious new adversary.
                  val popularity = ds.child("popularity").value as Double  // 7931.499
                  val posterPath= ds.child("posterPath").value.toString()  // /9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg
                  val releaseDate= ds.child("releaseDate").value.toString() // 2022-05-04
                  val title = ds.child("title").value.toString() // Doctor Strange in the Multiverse of Madness
                  val voteAverage= 0.0 // 7.5
                  val voteCount= ds.child("voteCount").value as Long // 3987
////
                  val model = Movie(adult, backdropPath, genreIds, id, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, voteAverage, voteCount)
//                  Log.d(TAG, "${ds.value}")
                  favArrayList.add(model)

//                  val model = MovieListResponse.Result()
               }

               adapterFav = FavAdapter(requireContext(), favArrayList)

               binding!!.rlMovies.adapter = adapterFav
            }

            override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
            }
         })
   }

}