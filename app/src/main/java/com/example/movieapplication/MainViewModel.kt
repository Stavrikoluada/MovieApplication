package com.example.movieapplication

import android.content.Context
import android.net.ConnectivityManager
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

    fun loadMoviesFromList(moviesList: List<MovieModel>) {
        _movies.postValue(moviesList.map { movieEntity ->
            MovieModel(
                id = movieEntity.id,
                title = movieEntity.title,
                overview = movieEntity.overview,
                poster = movieEntity.poster,
                backdrop = movieEntity.backdrop,
                ratings = movieEntity.ratings,
                ratingCount = movieEntity.ratingCount,
                minimumAge = movieEntity.minimumAge,
                like = movieEntity.like,
                genres = movieEntity.genres
            )
        })
    }

    fun loadMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                movieRepository.updateGenresMap(apiKey)
                val movieList = movieRepository.getPopularMovies(apiKey)
                _movies.postValue(movieList)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading movies", e)
                val moviesFromDb = movieRepository.getMoviesFromDatabase()
                _movies.postValue(moviesFromDb.map { movieEntity ->
                    MovieModel(
                        id = movieEntity.id,
                        title = movieEntity.title,
                        overview = movieEntity.overview,
                        poster = movieEntity.poster,
                        backdrop = movieEntity.backdrop,
                        ratings = movieEntity.ratings,
                        ratingCount = movieEntity.ratingCount,
                        minimumAge = movieEntity.minimumAge,
                        like = movieEntity.like,
                        genres = movieEntity.genres
                    )
                })
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


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    fun updateMovieLikeState(updatedMovie: MovieModel) {
        val currentList = _movies.value.orEmpty()
        val updatedList = currentList.map {
            if (it.id == updatedMovie.id) updatedMovie else it
        }
        _movies.postValue(updatedList)
    }
}