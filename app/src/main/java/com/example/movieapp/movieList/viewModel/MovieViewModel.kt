package com.example.movieapp.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movieapp.models.ListItem
import com.example.movieapp.models.Movie
import com.example.movieapp.movieList.repository.MovieRepository
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

//    val movieListPaging: StateFlow<PagingData<Movie>> =
//        Pager(
//            PagingConfig(pageSize = 1)
//        ) {
//            movieRepository.getMoviePagingSource()
//        }.flow
//            .map {pagingData ->
//                pagingData
//                    .map {movie -> movie }
//                    .insertSeparators { before: ListItem?, after: ListItem? ->
//                        if (before == null && after == null) {null}
//                        else if (after == null) { TODO("FOOTER")}
//                        else if (before == null) { TODO("HEADER")}
//                        else { null }
//                    }
//            }
//            .cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val movieMultipleListPaging: StateFlow<PagingData<ListItem>> =
        Pager(
            PagingConfig(pageSize = 1)
        ) {
            movieRepository.getMoviePagingSource()
        }.flow
//            .map {pagingData ->
//                pagingData
//                    .map {movie -> movie }
//                    .insertSeparators { before: ListItem?, after: ListItem? ->
//                        if (before == null && after == null) {null}
//                        else if (after == null) { TODO("FOOTER")}
//                        else if (before == null) { TODO("HEADER")}
//                        else { null }
//                    }
//            }
            .cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun addToFavourite(movie: Movie) {
        viewModelScope.launch(Dispatchers.Main) {
            movieRepository.addToFavourite(movie){
                _addToFavStatus.postValue(it)
            }
        }
    }

//    val moviePaging: Flow<PagingData<Movie>> = movieRepository.getPagedMovieList().cachedIn(viewModelScope)
//    init {
//        getMovieList(1)
//    }
}