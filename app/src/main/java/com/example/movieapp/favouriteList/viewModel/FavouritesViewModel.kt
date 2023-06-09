package com.example.movieapp.favouriteList.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.favouriteList.repository.FavouritesRepository
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(private val favouritesRepository: FavouritesRepository): ViewModel() {
    private val _favouritesListStatus = MutableLiveData<Result<List<Movie>>>()
    val favouritesListStatus: LiveData<Result<List<Movie>>> = _favouritesListStatus

    fun getFavoritesList() {
        _favouritesListStatus.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            favouritesRepository.getFavouritesList {
                _favouritesListStatus.postValue(it)
            }
        }
    }
}