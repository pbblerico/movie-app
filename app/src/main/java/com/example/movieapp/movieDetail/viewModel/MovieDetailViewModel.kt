package com.example.movieapp.movieDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.movieDetail.repository.MovieDetailRepository
import com.example.movieapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieDetailRepository: MovieDetailRepository): ViewModel() {
    private val _movieDetailState = MutableLiveData<Result<Movie>>()
    val movieDetailState: LiveData<Result<Movie>> = _movieDetailState

    fun getMovieDetail(id: Long) {
        _movieDetailState.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieDetailRepository.getMovieDetail(id)
            _movieDetailState.postValue(result)
        }
    }
}