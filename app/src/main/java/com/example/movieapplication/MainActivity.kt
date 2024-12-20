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

        initRecyclerView()

        // Наблюдаем за изменениями в данных фильмов
        viewModel.movies.observe(this, Observer { movies ->
            // Обновляем адаптер данными
            adapter.submitList(movies)
        })

        // Загружаем данные о фильмах
        val apiKey = "e47bdf1afdfecf01d05bec8e7ed9db25"
        viewModel.loadMovies(apiKey)
    }

    private fun initRecyclerView() {
        binding.rcView.layoutManager = GridLayoutManager(this, 2)
        adapter = MainMovieAdapter { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("MOVIE_ID", movie.title)
            intent.putExtra("MOVIE_ID", movie.title)
            startActivity(intent)
        }
        binding.rcView.adapter = adapter
    }
}

