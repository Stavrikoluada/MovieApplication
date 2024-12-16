package com.example.movieapplication.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.R
import com.example.movieapplication.data.MovieModel
import com.example.movieapplication.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MainMovieAdapter(private val clickListener: (MovieModel) -> Unit): RecyclerView.Adapter<MainMovieAdapter.ViewHolder>() {

    //Test
    var movieList = ArrayList<MovieModel>()

    class ViewHolder(item: View, private val clickListener: (MovieModel) -> Unit): RecyclerView.ViewHolder(item) {
        val binding = ItemMovieBinding.bind(item)
        @SuppressLint("SetTextI18n")
        fun bind(movieModel: MovieModel) = with(binding) {
            //Picasso.get().load("https:" + movieModel.imageUrl).transform(RoundedCornersTransformation(10, 0)).into(ivBackFonFragment)
            ageFragment.text = "${movieModel.ageMin}+"
            tvGenreMovie.text = movieModel.genres
            tvTitleFragment.text = movieModel.title
            tvTime.text = movieModel.time
            starText.text = "${movieModel.reviews} Reviews"
            setStar(movieModel.starCount)

            root.setOnClickListener { clickListener(movieModel) }
        }

        private fun setStar(starCount: Int) = with(binding) {
            val normCount = starCount / 2
            if (normCount >= 1) {
                star1f.setImageResource(R.drawable.star_red)
                if(normCount >= 2) {
                    star2f.setImageResource(R.drawable.star_red)
                    if(normCount >= 3) {
                        star3f.setImageResource(R.drawable.star_red)
                        if(normCount >= 4) {
                            star4f.setImageResource(R.drawable.star_red)
                            if(normCount >= 5) {
                                star5f.setImageResource(R.drawable.star_red)
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }


    fun addMovie(movieModel: MovieModel) {
        movieList.add(movieModel)
        notifyDataSetChanged()
    }

}