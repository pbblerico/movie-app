package com.example.movieapp.favouriteList.repository

import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.EventListener

//class FavsRepositoryImpl: FavsRepository {
//    override suspend fun getFavsList(): Result<List<Movie>> {
//        withContext(Dispatchers.IO) {
////            val favArrayList: ArrayList<Movie> = ArrayList()
////
////            Constants.ref.getReference("Users")
////                .child(Constants.auth.uid!!)
////                .child("Liked")
////                .addValueEventListener(object : ValueEventListener {
////                    override fun onDataChange(snapshot: DataSnapshot) {
////                        favArrayList.clear()
//////               Log.d(TAG, "${snapshot.value}")
////                        for (ds in snapshot.children) {
////                            val adult = ds.child("adult").value as Boolean // false
////                            val backdropPath = ds.child("backdropPath").value.toString()
////                            val genreIds = ds.child("genreIds").value as List<Int>
////                            val id = ds.child("id").value as Long
////                            val originalLanguage = ds.child("originalLanguage").value.toString()
////                            val originalTitle= ds.child("originalTitle").value.toString()
////                            val overview = ds.child("overview").value.toString() // Doctor Strange, with the help of mystical allies both old and new, traverses the mind-bending and dangerous alternate realities of the Multiverse to confront a mysterious new adversary.
////                            val popularity = ds.child("popularity").value as Double  // 7931.499
////                            val posterPath= ds.child("posterPath").value.toString()  // /9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg
////                            val releaseDate= ds.child("releaseDate").value.toString() // 2022-05-04
////                            val title = ds.child("title").value.toString() // Doctor Strange in the Multiverse of Madness
////                            val voteAverage= 0.0 // 7.5
////                            val voteCount= ds.child("voteCount").value as Long // 3987
////////
////                            val model = Movie(adult, backdropPath, genreIds, id, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, voteAverage, voteCount)
//////                  Log.d(TAG, "${ds.value}")
////                            favArrayList.add(model)
////                    }
////
////                    override fun onCancelled(error: DatabaseError) {
////                        TODO("Not yet implemented")
////                    }
////
////                })
////        }
//    }
//}