package com.example.movieapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.adapters.MainMovieAdapter
import com.example.movieapplication.api.provideMovieRepository
import com.example.movieapplication.api.provideMoviesApi
import com.example.movieapplication.api.provideRetrofit
import com.example.movieapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainMovieAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализируем ViewModel
        val retrofit = provideRetrofit()
        val moviesApi = provideMoviesApi(retrofit)
        val movieRepository = provideMovieRepository(moviesApi)
        viewModel = ViewModelProvider(this, MainViewModel.MainViewModelFactory(movieRepository))
            .get(MainViewModel::class.java)

        val apiKey = "e47bdf1afdfecf01d05bec8e7ed9db25"

        CoroutineScope(Dispatchers.IO).launch {
            movieRepository.updateGenresMap(apiKey)
            viewModel.loadMovies(apiKey)
        }
        initRecyclerView()

        // Наблюдаем за изменениями в данных фильмов
        viewModel.movies.observe(this, Observer { movies ->
            adapter.submitList(movies)
        })
    }

    private fun initRecyclerView() {
        binding.rcView.layoutManager = GridLayoutManager(this, 2)
        adapter = MainMovieAdapter { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java).apply {
                putExtra("MOVIE_ID", movie.id)
                putExtra("MOVIE_TITLE", movie.title)
                putExtra("MOVIE_MINIMUM_ADE", movie.minimumAge)
                putExtra("MOVIE_GENRES", movie.genres)
                putExtra("MOVIE_OVERVIEW", movie.overview)
                putExtra("MOVIE_BACKDROP", movie.backdrop)
                putExtra("MOVIE_RATING", movie.ratings)
                putExtra("MOVIE_RATING_COUNT", movie.ratingCount)
            }
            startActivity(intent)
        }
        binding.rcView.adapter = adapter
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val MOVIE_TITLE = "MOVIE_TITLE"
        const val MOVIE_MINIMUM_ADE = "MOVIE_MINIMUM_ADE"
        const val MOVIE_GENRES = "MOVIE_GENRES"
        const val MOVIE_OVERVIEW = "MOVIE_OVERVIEW"
        const val MOVIE_BACKDROP = "MOVIE_BACKDROP"
        const val MOVIE_RATING= "MOVIE_RATING"
        const val MOVIE_RATING_COUNT= "MOVIE_RATING_COUNT"
    }
}

