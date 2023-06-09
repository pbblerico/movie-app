package com.example.movieapp.favouriteList.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.movieapp.FavouriteAdapterClickListener
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.adapter.FavouritesAdapter
import com.example.movieapp.databinding.FragmentFavouriteBinding
import com.example.movieapp.favouriteList.viewModel.FavouritesViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Result
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavouriteFragment : Fragment(R.layout.fragment_favourite), FavouriteAdapterClickListener {
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
      favouritesAdapter = FavouritesAdapter(this)
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

   override fun removeFromFavourite(id: String) {
      val builder = AlertDialog.Builder(context)
      builder.setTitle("Delete")
         .setMessage("Are you sure you want to remove this book from favourites?")
         .setPositiveButton("Confirm"){a, d ->
            Toast.makeText(context, "Deleting", Toast.LENGTH_SHORT).show()
            viewModel.removeFromFavourite(id)
         }
         .setNegativeButton("Cancel") {a, d ->
            a.dismiss()
         }.show()
   }

   override fun onItemClick(movie: Movie) {
      val action = FavouriteFragmentDirections.favToMovieDetailFragment(movie.id)
      Navigation.findNavController(requireView()).navigate(action)
   }
}