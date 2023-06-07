package com.example.movieapp.authorization.viewModel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.authorization.repository.AuthRepository
import com.example.movieapp.utils.Result
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository): ViewModel() {
    private val _signUpStatus = MutableLiveData<Result<AuthResult>>()
    val signUpStatus: LiveData<Result<AuthResult>> = _signUpStatus

    fun signUp(name: String, surname: String, email: String, password1: String, password2: String) {
        val error =
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                "Empty Inputs"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                "Not a valid Email"
            } else if(password1 != password2) {
                "Wrong Password Repeated"
            }
            else null

        error?.let {
            _signUpStatus.postValue(Result.Failure(it))
            return
        }
        _signUpStatus.postValue(Result.Loading())

        viewModelScope.launch(Dispatchers.Main) {
            val registerResult = authRepository.signUp(name = name, surname = surname, email = email, password = password1)
            _signUpStatus.postValue(registerResult)
        }
    }
}