package com.example.movieapp.authorization.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.authorization.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.example.movieapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {
    private val _loginStatus = MutableLiveData<Result<AuthResult>>()
    val loginStatus: LiveData<Result<AuthResult>> = _loginStatus

    fun login(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()) {
            _loginStatus.postValue(Result.Failure("Blank fields"))
        } else {
            _loginStatus.postValue(Result.Loading())
            viewModelScope.launch(Dispatchers.Main){
                val loginResult = authRepository.login(email, password)
                _loginStatus.postValue(loginResult)
            }
        }
    }
}