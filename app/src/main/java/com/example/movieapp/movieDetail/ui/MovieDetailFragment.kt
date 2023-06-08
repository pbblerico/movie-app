package com.example.movieapp.movieDetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.databinding.FragmentMovieDetailBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.movieDetail.viewModel.MovieDetailViewModel
import com.example.movieapp.utils.Constants.POSTER_BASE_URL
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.movieapp.utils.Result


class MovieDetailFragment : Fragment() {
    private var binding: FragmentMovieDetailBinding? = null
    private val viewModel by viewModel<MovieDetailViewModel>()
    val args: MovieDetailFragmentArgs by navArgs()
    private var id: Long? = null

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        id = args.id

       observeData()
        return binding!!.root
    }

    private fun observeData() {
        viewModel.getMovieDetail(id!!)

        viewModel.movieDetailState.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Loading -> {
                    (requireActivity() as MainActivity).showProgressBar()
                }
                is Result.Success -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    setData(it.data!!)
                }
                is Result.Failure -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                }
                is Result.Empty -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                }
            }
        }
    }

    private fun setData(movie: Movie) {
        val moviePosterURL = POSTER_BASE_URL + movie.posterPath
        binding!!.apply {
            imgMovie.load(moviePosterURL) {
                crossfade(true)
                placeholder(R.drawable.poster_placeholder)
                scale(Scale.FILL)
            }
            tvMovieTitle.text = movie.title
            tvMovieDateRelease.text = movie.releaseDate
            tvMovieRating.text = movie.voteAverage.toString()
            tvMovieOverview.text = movie.overview
        }
    }
}