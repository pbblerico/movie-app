package com.example.movieapp.authorization.repository

import com.google.firebase.auth.AuthResult
import com.example.movieapp.utils.Result

interface AuthRepository {
    suspend fun signUp(name: String, surname: String, email: String, password: String): Result<AuthResult>

    suspend fun login(email: String, password: String): Result<AuthResult>

    fun logout()
}