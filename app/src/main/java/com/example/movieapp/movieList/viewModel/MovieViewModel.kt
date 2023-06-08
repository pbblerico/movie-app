package com.example.movieapp.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.movieList.repository.MovieRepository
import com.example.movieapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val _movieListStatus = MutableLiveData<Result<List<Movie>>>()
    val movieListStatus: LiveData<Result<List<Movie>>> = _movieListStatus

    private val _movieDetailStatus = MutableLiveData<Result<Movie>>()
    val movieDetailStatus: LiveData<Result<Movie>> = _movieDetailStatus


    fun getMovieList(page: Int) {
        _movieListStatus.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieRepository.getMovieList(page)
            _movieListStatus.postValue(result)
        }
    }

    fun getMovieDetails(id: Long) {
        _movieDetailStatus.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieRepository.getMovieDetail(id)
            _movieDetailStatus.postValue(result)
        }
    }
}