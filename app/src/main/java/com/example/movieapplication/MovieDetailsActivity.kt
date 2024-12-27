package com.example.movieapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.MainActivity.Companion.MOVIE_BACKDROP
import com.example.movieapplication.MainActivity.Companion.MOVIE_GENRES
import com.example.movieapplication.MainActivity.Companion.MOVIE_ID
import com.example.movieapplication.MainActivity.Companion.MOVIE_MINIMUM_ADE
import com.example.movieapplication.MainActivity.Companion.MOVIE_OVERVIEW
import com.example.movieapplication.MainActivity.Companion.MOVIE_RATING
import com.example.movieapplication.MainActivity.Companion.MOVIE_RATING_COUNT
import com.example.movieapplication.MainActivity.Companion.MOVIE_TITLE
import com.example.movieapplication.adapters.DetailsActorsAdapter
//import com.example.movieapplication.api.provideMovieRepository
import com.example.movieapplication.api.provideMoviesApi
import com.example.movieapplication.api.provideRetrofit
import com.example.movieapplication.data.ActorsModel
import com.example.movieapplication.databinding.ActivityMovieDetailsBinding
import com.example.movieapplication.db.AppDatabase
import com.example.movieapplication.repository.MovieRepository
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movieRepository: MovieRepository // добавим репозиторий
    private lateinit var apiKey: String
    private lateinit var actorsAdapter: DetailsActorsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val movieId = intent.getLongExtra(MOVIE_ID, -1)   // Получаем ID фильма
        val movieTitle = intent.getStringExtra(MOVIE_TITLE) ?: "Unknown Title"
        val movieMinimumAge = intent.getBooleanExtra(MOVIE_MINIMUM_ADE, false)
        val movieGenres = intent.getStringExtra(MOVIE_GENRES) ?: "Unknown Genres"
        val movieOverview = intent.getStringExtra(MOVIE_OVERVIEW) ?: "No Overview"
        val movieBackdrop = intent.getStringExtra(MOVIE_BACKDROP) ?: ""
        val movieRating = intent.getFloatExtra(MOVIE_RATING, 0f)
        val movieRatingCount = intent.getLongExtra(MOVIE_RATING_COUNT, 0L)

        binding.tvTitleMovie.text = movieTitle
        binding.tvAge.text = "${if (movieMinimumAge) 16 else 13}+"
        binding.tvGenreMovie.text = movieGenres
        binding.tvStorLineDes.text = movieOverview
        Picasso.get().load("https://image.tmdb.org/t/p/w500/$movieBackdrop")
            .into(binding.back)
        binding.starText.text = "${movieRatingCount} Reviews"
        setStar(movieRating.toInt())


        binding.btBack.setOnClickListener {
            finish()
        }

        // Устанавливаем имя для перехода
        val transitionName = intent.getStringExtra("movie_container_transition")
        binding.main.transitionName = transitionName

        // Настройка анимации перехода
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 500
            scrimColor = Color.TRANSPARENT
        }

        // Инициализируем репозиторий
        val retrofit = provideRetrofit()
        val moviesApi = provideMoviesApi(retrofit)
        val movieDao = AppDatabase.getDatabase(this).movieDao()
        val genreDao = AppDatabase.getDatabase(this).genreDao()
        movieRepository = MovieRepository(moviesApi, movieDao, genreDao)
        viewModel = ViewModelProvider(this, MainViewModel.MainViewModelFactory(movieRepository))
            .get(MainViewModel::class.java)

        apiKey = "e47bdf1afdfecf01d05bec8e7ed9db25"

        actorsAdapter = DetailsActorsAdapter(emptyList())

        binding.rvActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvActors.adapter = actorsAdapter

        CoroutineScope(Dispatchers.IO).launch {
            if (viewModel.isNetworkAvailable(this@MovieDetailsActivity)) {
                val actors = movieRepository.getActorsForId(movieId, apiKey)
                withContext(Dispatchers.Main) {
                    actors?.let {
                        actorsAdapter = DetailsActorsAdapter(it)
                        binding.rvActors.adapter = actorsAdapter
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    val actors: List<ActorsModel> = listOf(ActorsModel("", ""))
                    actors?.let {
                        actorsAdapter = DetailsActorsAdapter(it)
                        binding.rvActors.adapter = actorsAdapter
                    }
                }
            }
        }
    }

    private fun setStar(starCount: Int) = with(binding) {
        val normCount = starCount / 2
        if (normCount >= 1) {
            binding.star1.setImageResource(R.drawable.star_red)
            if(normCount >= 2) {
                binding.star2.setImageResource(R.drawable.star_red)
                if(normCount >= 3) {
                    binding.star3.setImageResource(R.drawable.star_red)
                    if(normCount >= 4) {
                        binding.star4.setImageResource(R.drawable.star_red)
                        if(normCount >= 5) {
                            binding.star5.setImageResource(R.drawable.star_red)
                        }
                    }
                }
            }
        }
    }

}