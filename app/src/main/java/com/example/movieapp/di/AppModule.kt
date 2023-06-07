package com.example.movieapp.di

import com.example.movieapp.authorization.repository.AuthRepository
import com.example.movieapp.authorization.repository.AuthRepositoryImpl
import com.example.movieapp.authorization.viewModel.LoginViewModel
import com.example.movieapp.authorization.viewModel.SignUpViewModel
import com.example.movieapp.retrofit.api.ApiClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> {
        return@single AuthRepositoryImpl()
    }
    single {
        ApiClient().getClient()
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        SignUpViewModel(get())
    }
}