package com.example.movieapp.movieList.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.movieList.viewModel.MovieViewModel
import com.example.movieapp.retrofit.api.ApiClient
import com.example.movieapp.retrofit.api.ApiService
import com.example.movieapp.utils.Result
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment(R.layout.fragment_movie) {
   private var binding: FragmentMovieBinding? = null
   private val viewModel by viewModel<MovieViewModel>()

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

      binding!!.pageTV.setOnClickListener { pagePickDialog() }
//      loadMovies()
      load()

      return binding!!.root
   }

   private fun load() {
      viewModel.getMovieList(binding!!.pageTV.text.toString().toInt())

      viewModel.movieListStatus.observe(viewLifecycleOwner) { it ->
         when(it) {
            is Result.Loading -> {
               (requireActivity() as MainActivity).showProgressBar()
            }
            is Result.Success -> {
//               Toast.makeText(requireContext(), "Works", Toast.LENGTH_SHORT).show()
//               Log.d(TAG, "${it.data.forEach {  }}")
               it.data!!.forEach {m ->
                  Log.d(TAG, "${m.id}")
               }
            }

            is Result.Failure -> {
               Toast.makeText(requireContext(), "Sorry", Toast.LENGTH_SHORT).show()
            }
         }
      }
   }

   private fun pagePickDialog() {
      val pagesArray = arrayOfNulls<String>(10)
      for(i in pagesArray.indices) {
         pagesArray[i] = "${i + 1}"
      }
      val builder = AlertDialog.Builder(requireContext())
      builder.setTitle("Pick Page")
         .setItems(pagesArray) { _, which ->
            binding!!.pageTV.text = pagesArray[which]
            load()
         }.show()
   }
}