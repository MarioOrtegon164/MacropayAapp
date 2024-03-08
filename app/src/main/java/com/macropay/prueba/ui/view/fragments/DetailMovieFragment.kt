package com.macropay.prueba.ui.view.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.macropay.prueba.ui.viewmodel.DetailMovieViewModel
import com.macropay.prueba.R
import com.macropay.prueba.data.model.Movie
import com.macropay.prueba.data.repositories.MyRepository
import com.macropay.prueba.databinding.FragmentDetailMovieBinding
import com.macropay.prueba.databinding.FragmentHomeBinding
import com.macropay.prueba.ui.adapters.MovieAdapter
import com.macropay.prueba.ui.viewmodel.HomeViewModel
import com.macropay.prueba.ui.viewmodel.MyViewModelFactory
import com.squareup.picasso.Picasso

class DetailMovieFragment : Fragment() {

    companion object {
        fun newInstance() = DetailMovieFragment()
    }

    private lateinit var detailMovieViewModel: DetailMovieViewModel
    private lateinit var fragmentDetailMovieBinding:FragmentDetailMovieBinding
    private lateinit var clickedMovie:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = MyRepository()
        val viewModelFactory = MyViewModelFactory(repository)
        detailMovieViewModel = ViewModelProvider(this,viewModelFactory)[DetailMovieViewModel::class.java]

        clickedMovie = arguments?.getString("movie").toString()
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var gson= Gson()
        var movie = gson.fromJson(clickedMovie, Movie::class.java)
        fragmentDetailMovieBinding = FragmentDetailMovieBinding.inflate(inflater, container, false)

        //setupEvents()
        //setupObservers()

        val apiKey = "c0823934438075d63f1dbda4023e76fc"
        detailMovieViewModel.dataList.observe(viewLifecycleOwner) {
            var movieGenres = ""

            it.genres.forEach {genre ->
                movieGenres +=  genre.name + " "
            }

            fragmentDetailMovieBinding.tvDetailMovieTitle.text = it.title
            fragmentDetailMovieBinding.tvDetailMovieDuration.text = "Duracion: " + it.runtime + " minutos"
            fragmentDetailMovieBinding.tvDetailMovieYear.text = "Fecha de lanzamiento: " + it.releaseDate
            fragmentDetailMovieBinding.tvDetailMovieGenres.text = "Generos: $movieGenres"
            fragmentDetailMovieBinding.tvDetailMovieDesc.text = "Descripcion: " + it.overview

            val urlImage = "https://image.tmdb.org/t/p/original"
            Picasso.get()
                .load(urlImage + it.posterPath)
                .placeholder(R.drawable.ic_no_image) // Placeholder while the image is loading
                .resize(600, 900)
                .centerCrop()
                .error(R.drawable.ic_no_image) // Error image if the loading fails
                .into(fragmentDetailMovieBinding.imageViewMovie)

        }

        detailMovieViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // Maneja el error, muestra un mensaje al usuario, etc.
        }
        detailMovieViewModel.getMovieDetail(apiKey,movie.id)

        return fragmentDetailMovieBinding.root
    }

}