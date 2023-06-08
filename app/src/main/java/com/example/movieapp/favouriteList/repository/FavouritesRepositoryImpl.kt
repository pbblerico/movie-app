package com.example.movieapp.favouriteList.repository

import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.movieapp.utils.Result

class FavouritesRepositoryImpl: FavouritesRepository{
    override suspend fun getFavouritesList(result: (Result<List<Movie>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val favArrayList: ArrayList<Movie> = ArrayList()

                Constants.ref.getReference("Users")
                    .child(Constants.auth.uid!!)
                    .child("Liked")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            favArrayList.clear()

                            for (ds in snapshot.children) {
                                val model = ds.getValue(Movie::class.java)

                                favArrayList.add(model!!)
                            }

                            result.invoke(
                                Result.Success(favArrayList.toList())
                            )
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })


            } catch (e: java.lang.Exception) {
                result.invoke(
                    Result.Failure(e.message ?: e.localizedMessage)
                )
            }
        }
    }
}