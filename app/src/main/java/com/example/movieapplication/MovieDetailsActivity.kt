package com.example.movieapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapplication.MainActivity.Companion.MOVIE_BACKDROP
import com.example.movieapplication.MainActivity.Companion.MOVIE_GENRES
import com.example.movieapplication.MainActivity.Companion.MOVIE_ID
import com.example.movieapplication.MainActivity.Companion.MOVIE_MINIMUM_ADE
import com.example.movieapplication.MainActivity.Companion.MOVIE_OVERVIEW
import com.example.movieapplication.MainActivity.Companion.MOVIE_RATING
import com.example.movieapplication.MainActivity.Companion.MOVIE_RATING_COUNT
import com.example.movieapplication.MainActivity.Companion.MOVIE_TITLE
import com.example.movieapplication.databinding.ActivityMovieDetailsBinding
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailsBinding

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