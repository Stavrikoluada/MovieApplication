package com.example.movieapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.data.MovieModel
import com.example.movieapplication.repository.MovieRepository

class MainViewModel: ViewModel() {

    private val _movies = MutableLiveData<List<MovieModel>>()
    val movies: LiveData<List<MovieModel>> get() = _movies

    private val repository = MovieRepository()

    // Функция для загрузки данных о фильмах
    fun loadMovies() {
        // Здесь можно вызвать репозиторий или сетевой запрос для получения данных
        // Пример:
        val movieList = repository.getMovies()
        _movies.postValue(movieList)
    }

    override fun onCleared() {
        super.onCleared()
        // Очистка ресурсов, если это необходимо
    }
}