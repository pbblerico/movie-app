package com.example.movieapp.movieList.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.retrofit.api.ApiClient
import com.example.movieapp.retrofit.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment(R.layout.fragment_movie) {
   private lateinit var binding: FragmentMovieBinding

   private lateinit var mainActivity: MainActivity
   private lateinit var cont: Context
   private val TAG = "MOVIE"
   private val movieAdapter by lazy { MovieAdapter() }

   private val api: ApiService by lazy {
      ApiClient().getClient().create(ApiService::class.java)
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      binding = FragmentMovieBinding.inflate(inflater, container, false)

      binding.pageTV.setOnClickListener { pagePickDialog() }
      loadMovies()


      return binding.root
   }

   private fun loadMovies() {
      binding.apply {
         //show loading
         prgBarMovies.visibility = View.VISIBLE
         //Call movies api
         val callMoviesApi = api.getPopularMovie(pageTV.text.toString().toInt())
         callMoviesApi.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
               prgBarMovies.visibility = View.GONE
               when (response.code()) {
                  in 200..299 -> {
                     Log.d("Response Code", " success messages : ${response.code()}")
                     response.body()?.let { itBody ->
                        itBody.results.let { itData ->
                           if (itData.isNotEmpty()) {
                              movieAdapter.differ.submitList(itData)
                              //Recycler
                              rlMovies.apply {
                                 layoutManager = LinearLayoutManager(cont)
                                 adapter = movieAdapter
                              }
                           }
                        }
                     }
                  }
                  in 300..399 -> {
                     Log.d("Response Code", " Redirection messages : ${response.code()}")
                  }
                  in 400..499 -> {
                     Log.d("Response Code", " Client error responses : ${response.code()}")
                  }
                  in 500..599 -> {
                     Log.d("Response Code", " Server error responses : ${response.code()}")
                  }
               }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
               prgBarMovies.visibility = View.GONE
               Log.e("onFailure", "Err : ${t.message}")
            }
         })
      }
   }

   private fun pagePickDialog() {
      val pagesArray = arrayOfNulls<String>(10)
      for(i in pagesArray.indices) {
         pagesArray[i] = "${i + 1}"
      }
      val builder = AlertDialog.Builder(cont)
      builder.setTitle("Pick Page")
         .setItems(pagesArray) { _, which ->
            binding.pageTV.text = pagesArray[which]
            loadMovies()
         }.show()
   }

   override fun onAttach(context: Context) {
      mainActivity = activity as MainActivity
      cont = context
      super.onAttach(context)
   }

}