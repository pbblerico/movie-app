package com.example.movieapp.favouriteList.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.adapter.FavouritesAdapter
import com.example.movieapp.databinding.FragmentFavouriteBinding
import com.example.movieapp.favouriteList.viewModel.FavouritesViewModel
import com.example.movieapp.utils.Result
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavouriteFragment : Fragment(R.layout.fragment_favourite) {
   private var binding: FragmentFavouriteBinding? = null
   private var favouritesAdapter: FavouritesAdapter? = null
   private val viewModel by viewModel<FavouritesViewModel>()
   private val TAG = "FAV"
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      binding = FragmentFavouriteBinding.inflate(inflater, container, false)
      favouritesAdapter = FavouritesAdapter()
      binding!!.rlMovies.adapter = favouritesAdapter
      load()


      return binding!!.root
   }

   private fun load() {
      viewModel.getFavoritesList()

      viewModel.favouritesListStatus.observe(viewLifecycleOwner) {
         when (it) {
            is Result.Loading -> {
               (requireActivity() as MainActivity).showProgressBar()
            }
            is Result.Success -> {
               it.data!!.forEach {  m ->
                  Log.d(TAG, m.toString())
               }

               (requireActivity() as MainActivity).hideProgressBar()
               favouritesAdapter!!.submitList(it.data)
            }

            is Result.Failure -> {
               (requireActivity() as MainActivity).hideProgressBar()
               Toast.makeText(requireContext(), "Sorry", Toast.LENGTH_SHORT).show()
            }

            is Result.Empty -> {
               (requireActivity() as MainActivity).hideProgressBar()
               Toast.makeText(requireContext(), "Page is empty", Toast.LENGTH_SHORT).show()
            }
         }
      }
   }
}