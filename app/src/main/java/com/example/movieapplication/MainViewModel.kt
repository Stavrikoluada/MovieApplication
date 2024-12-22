package com.example.movieapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.data.MovieModel
import com.example.movieapplication.repository.MovieRepository
import kotlinx.coroutines.launch


class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieModel>>()
    val movies: LiveData<List<MovieModel>> get() = _movies

    fun loadMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Loading popular movies...")
                val movieList = movieRepository.getPopularMovies(apiKey)
                _movies.postValue(movieList)
                Log.d("MainViewModel", "Movies loaded, total count: ${movieList.size}")
            } catch (e: Exception) {
                // Обработка ошибки, если запрос не удался
                Log.e("MainViewModel", "Error loading movies", e)
            }
        }
    }

    class MainViewModelFactory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(movieRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}