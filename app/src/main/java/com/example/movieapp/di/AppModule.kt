package com.example.movieapp.di

import com.example.movieapp.authorization.repository.AuthRepository
import com.example.movieapp.authorization.repository.AuthRepositoryImpl
import com.example.movieapp.authorization.viewModel.LoginViewModel
import com.example.movieapp.authorization.viewModel.SignUpViewModel
import com.example.movieapp.favouriteList.repository.FavouritesRepository
import com.example.movieapp.favouriteList.repository.FavouritesRepositoryImpl
import com.example.movieapp.favouriteList.viewModel.FavouritesViewModel
import com.example.movieapp.movieDetail.repository.MovieDetailRepository
import com.example.movieapp.movieDetail.repository.MovieDetailRepositoryImpl
import com.example.movieapp.movieDetail.viewModel.MovieDetailViewModel
import com.example.movieapp.movieList.repository.MovieRepository
import com.example.movieapp.movieList.repository.MovieRepositoryImpl
import com.example.movieapp.movieList.viewModel.MovieViewModel
import com.example.movieapp.retrofit.api.ApiService
import com.example.movieapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single<AuthRepository> { return@single AuthRepositoryImpl() }
    single { interceptor() }
    single { okhttpClient(get()) }
    single { retrofit(get()) }
    single { apiService(get()) }
    single<MovieRepository> { return@single MovieRepositoryImpl(get()) }
    single<FavouritesRepository> { return@single FavouritesRepositoryImpl()}
    single<MovieDetailRepository> { return@single MovieDetailRepositoryImpl(get()) }


    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { FavouritesViewModel(get()) }
    viewModel { MovieDetailViewModel(get())}
}

private fun interceptor(): Interceptor =
    Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }


private fun okhttpClient(requestInterceptor: Interceptor): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

    return OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addInterceptor(requestInterceptor)
    .connectTimeout(60, TimeUnit.SECONDS)
    .build()
}

private fun retrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun apiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)