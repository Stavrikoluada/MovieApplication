package com.example.movieapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.R
import com.example.movieapplication.data.ActorsModel
import com.squareup.picasso.Picasso

class DetailsActorsAdapter(private val actorsList: List<ActorsModel>) : RecyclerView.Adapter<DetailsActorsAdapter.ActorViewHolder>() {

    class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivActorImage: ImageView = itemView.findViewById(R.id.iv_actor_item)
        private val tvActorName: TextView = itemView.findViewById(R.id.tv_actor_name_item)

        fun bind(actor: ActorsModel) {
            tvActorName.text = actor.name
            if (actor.profilePath != null) {
                Picasso.get().load("https://image.tmdb.org/t/p/w500/" + actor.profilePath)
                    .transform(RoundedCornersTransformation(20f))
                    .into(ivActorImage)
            } else {
                ivActorImage.setImageResource(R.drawable.default_actor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actorsList[position]
        holder.bind(actor)
    }

    override fun getItemCount(): Int = actorsList.size
}
