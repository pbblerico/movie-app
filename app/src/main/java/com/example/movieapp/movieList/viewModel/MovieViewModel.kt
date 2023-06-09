package com.example.movieapp.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.models.Movie
import com.example.movieapp.movieList.repository.MovieRepository
import com.example.movieapp.paging.MoviePagingSource
import com.example.movieapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val _movieListStatus = MutableLiveData<Result<List<Movie>>>()
    val movieListStatus: LiveData<Result<List<Movie>>> = _movieListStatus


    private val _addToFavStatus = MutableLiveData<Result<String>>()
    val addToFavStatus: LiveData<Result<String>> = _addToFavStatus

    fun getMovieList(page: Int) {
        _movieListStatus.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieRepository.getMovieList(page)
            _movieListStatus.postValue(result)
        }
    }

    fun addToFavourite(movie: Movie) {
        viewModelScope.launch(Dispatchers.Main) {
            movieRepository.addToFavourite(movie){
                _addToFavStatus.postValue(it)
            }
        }
    }

//    val movieListPaging: StateFlow<PagingData<Movie>> = flow<PagingData<Movie>> {
//        movieRepository.getPagedMovieList()
//    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val moviePaging: Flow<PagingData<Movie>> = movieRepository.getPagedMovieList().cachedIn(viewModelScope)
    init {
        getMovieList(1)
    }
}