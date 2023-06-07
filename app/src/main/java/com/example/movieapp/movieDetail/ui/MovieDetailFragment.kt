package com.example.movieapp.movieDetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment() {
    private var binding: FragmentMovieDetailBinding? = null

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }
}