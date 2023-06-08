package com.example.movieapp.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.models.Movie
import com.example.movieapp.movieList.repository.MovieRepository
import com.example.movieapp.paging.PagingSourceImpl
import com.example.movieapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val _movieListStatus = MutableLiveData<Result<List<Movie>>>()
    val movieListStatus: LiveData<Result<List<Movie>>> = _movieListStatus


    fun getMovieList(page: Int) {
        _movieListStatus.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieRepository.getMovieList(page)
            _movieListStatus.postValue(result)
        }
    }

//    val movieListStates = movieRepository.getMovieListPaging()
//        .map {pagingData ->
//            pagingData.map {
//
//            }
//        }.cachedIn(viewModelScope)

//    fun movieList(page: Int) {
//        _movieListStatus.postValue(Result.Loading())
//        viewModelScope.launch(Dispatchers.Main) {
//            flow.collectLatest { pagingData ->
//                _movieListStatus.postValue(pagingData)
//            }
//        }
//    }

    val movieListPaging: StateFlow<PagingData<Movie>> = flow<PagingData<Movie>> {
        Pager(
            PagingConfig(pageSize = 20)
        ){
            PagingSourceImpl(movieRepository)
        }.flow
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}