package com.example.movieapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.adapters.MainMovieAdapter
import com.example.movieapplication.data.MovieModel
import com.example.movieapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var adapter: MainMovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rc_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

    }

    private fun init() {
        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = MainMovieAdapter { item ->
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("MOVIE_ID", item.title)
                startActivity(intent)
            }
            rcView.adapter = adapter
        }

        val movieModel1 = MovieModel(
            "15",
            "Comedi",
            "One one",
            "100",
            "30",
            8
        )
        val movieModel2 = MovieModel(
            "18",
            "Dramma",
            "My Home",
            "80",
            "20",
            4
        )
        val movieModel3 = MovieModel(
            "1",
            "Merrrrgrgdg fgfdbd fbbdfb",
            "Aple fdkjdfhjvb dhfjgbhfdb fb",
            "20",
            "2000",
            1
        )

        adapter?.addMovie(movieModel1)
        adapter?.addMovie(movieModel2)
        adapter?.addMovie(movieModel3)
    }
}