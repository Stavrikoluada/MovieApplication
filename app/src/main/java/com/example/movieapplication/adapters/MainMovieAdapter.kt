package com.example.movieapplication.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.R
import com.example.movieapplication.data.MovieModel
import com.example.movieapplication.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MainMovieAdapter(private val clickListener: (MovieModel) -> Unit) :
        ListAdapter<MovieModel, MainMovieAdapter.ViewHolder>(MovieDiffCallback()) {

    class ViewHolder(item: View, private val clickListener: (MovieModel) -> Unit): RecyclerView.ViewHolder(item) {
        val binding = ItemMovieBinding.bind(item)
        @SuppressLint("SetTextI18n")
        fun bind(movieModel: MovieModel) = with(binding) {

            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movieModel.poster)
                .transform(RoundedCornersTransformation(20f))
                .into(ivBackFragm)
            ageFragment.text = "${if (movieModel.minimumAge) 16 else 13}+"
            tvGenreMovie.text = movieModel.genres
            tvTitleFragment.text = movieModel.title
            starText.text = "${movieModel.ratingCount} Reviews"
            setStar(movieModel.ratings.toInt())

            if (movieModel.like) {
                like.setImageResource(R.drawable.like_red)
            } else {
                like.setImageResource(R.drawable.like)
            }
            val container = binding.mainF
            container.transitionName = "movie_container_${movieModel.id}"

            like.setOnClickListener {
                val newLikeState = !movieModel.like
                val updatedMovie = movieModel.copy(like = newLikeState)
                clickListener(updatedMovie)

            }

            root.setOnClickListener {
                clickListener(movieModel)
            }
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }
}