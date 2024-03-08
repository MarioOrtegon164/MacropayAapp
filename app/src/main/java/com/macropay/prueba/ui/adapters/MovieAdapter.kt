package com.macropay.prueba.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macropay.prueba.R
import com.macropay.prueba.data.model.Movie
import com.macropay.prueba.databinding.ItemMovieBinding
import com.macropay.prueba.utils.Constants
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat

class MovieAdapter(private val movies: List<Movie>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {

            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.FLOOR
            val formatNumber = df.format(movie.voteAverage)

            binding.tvMovieTitle.text = movie.title
            binding.tvMovieDesc.text = movie.overview.ifEmpty { "No se encontro una descripcion" }
            binding.tvMovieRate.text = formatNumber.toString()


            Picasso.get()
                .load(Constants().MOVIE_DB_IMAGE_URL + movie.posterPath)
                .placeholder(R.drawable.ic_no_image)
                .resize(600, 900)
                .centerCrop()
                .error(R.drawable.ic_no_image)
                .into(binding.ivMoviePortrait)

            binding.cardView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedMovie = movies[position]
                    onItemClickListener.onItemClick(clickedMovie)
                }
            }
        }
    }
}