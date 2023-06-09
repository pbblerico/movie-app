package com.example.movieapp.movieList.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.map
import com.example.movieapp.MovieAdapterClickListener
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.movieList.viewModel.MovieViewModel
import com.example.movieapp.utils.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapterClickListener {
   private var binding: FragmentMovieBinding? = null
   private val viewModel by viewModel<MovieViewModel>()

   private val TAG = "MOVIE"
   private var movieAdapter: MovieAdapter? = null

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      binding = FragmentMovieBinding.inflate(inflater, container, false)

      movieAdapter = MovieAdapter(this)

      binding!!.rlMovies.adapter = movieAdapter
      load()

      return binding!!.root
   }

   fun openDetailPage(id: Long) {
      Log.d(TAG, "page opened")
   }

   fun addToFavourite() {
      Log.d(TAG, "added to fav")
   }



   private fun load() {
//      viewModel.getMovieList(binding!!.pageTV.text.toString().toInt())


      lifecycleScope.launch {
         viewModel.moviePaging.collectLatest {pagingData ->
            movieAdapter?.submitData(pagingData)
//            pagingData.map {
//               Log.d(TAG, it.id.toString())
//            }
         }
      }


//      lifecycleScope.launch {
//         viewModel.movieListPaging.collectLatest {
//            movieAdapter?.submitData(it)
//         }
//      }
//      lifecycleScope.launch {
//         movieAdapter?.loadStateFlow?.collectLatest {
//            when (val loadState = it.refresh) {
//               is LoadState.Error -> {
//                  loadState.error
//               }
//            }
//         }
//      }

//      viewModel.movieListStatus.observe(viewLifecycleOwner) {
//         when(it) {
//            is Result.Loading -> {
//               (requireActivity() as MainActivity).showProgressBar()
//            }
//            is Result.Success -> {
//               (requireActivity() as MainActivity).hideProgressBar()
//               movieAdapter!!.submit(/*viewModel.movieListPaging*/it.data)
//            }
//
//            is Result.Failure -> {
//               (requireActivity() as MainActivity).hideProgressBar()
//               Toast.makeText(requireContext(), "Sorry", Toast.LENGTH_SHORT).show()
//            }
//
//            is Result.Empty -> {
//               Toast.makeText(requireContext(), "Page is empty", Toast.LENGTH_SHORT).show()
//            }
//         }
//      }
   }

   override fun onItemClick(movie: Movie) {
      val action = MovieFragmentDirections.toMovieDetailFragment(movie.id)
      Navigation.findNavController(requireView()).navigate(action)
   }

   override fun onLikeButtonClick(movie: Movie) {
      viewModel.addToFavourite(movie)
      viewModel.addToFavStatus.observe(viewLifecycleOwner) {
         when(it) {
            is Result.Loading -> {
               Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
            }
            is Result.Success -> {
               Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
            }

            else -> {
               Toast.makeText(requireContext(), "Sorry", Toast.LENGTH_SHORT).show()
            }
         }
      }
   }

//   private fun pagePickDialog() {
//      val pagesArray = arrayOfNulls<String>(10)
//      for(i in pagesArray.indices) {
//         pagesArray[i] = "${i + 1}"
//      }
//      val builder = AlertDialog.Builder(requireContext())
//      builder.setTitle("Pick Page")
//         .setItems(pagesArray) { _, which ->
//            binding!!.pageTV.text = pagesArray[which]
//            load()
//         }.show()
//   }
}