package com.example.movieapplication.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun provideRetrofit(): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
    return retrofit.create(MoviesApi::class.java)
}
