package com.macropay.prueba.ui.view.activities


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.macropay.prueba.R
import com.macropay.prueba.data.model.Movie
import com.macropay.prueba.data.repositories.MyRepository
import com.macropay.prueba.databinding.ActivityDetailMovieBinding
import com.macropay.prueba.ui.viewmodel.DetailMovieViewModel
import com.macropay.prueba.ui.viewmodel.MyViewModelFactory
import com.macropay.prueba.utils.Constants
import com.macropay.prueba.utils.Utils
import com.squareup.picasso.Picasso


class DetailMovieActivity : AppCompatActivity() {


    private lateinit var detailMovieViewModel: DetailMovieViewModel
    private lateinit var fragmentDetailMovieBinding: ActivityDetailMovieBinding
    private lateinit var clickedMovie:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentDetailMovieBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(fragmentDetailMovieBinding.root)

        val repository = MyRepository()
        val viewModelFactory = MyViewModelFactory(repository)
        detailMovieViewModel = ViewModelProvider(this,viewModelFactory)[DetailMovieViewModel::class.java]

        clickedMovie = intent.getStringExtra("movie").toString()

        showInfo()
    }


    @SuppressLint("SetTextI18n")
    private fun showInfo() {
        var gson= Gson()
        var movie = gson.fromJson(clickedMovie, Movie::class.java)

        detailMovieViewModel.dataList.observe(this) {
            var movieGenres = ""

            it.genres.forEach {genre ->
                movieGenres +=  genre.name + " "
            }

            fragmentDetailMovieBinding.tvDetailMovieTitle.text = it.title
            fragmentDetailMovieBinding.tvDetailMovieDuration.text = "Duracion: " + it.runtime + " minutos"
            fragmentDetailMovieBinding.tvDetailMovieYear.text = "Fecha de lanzamiento: " + Utils().formatDateString(it.releaseDate)
            fragmentDetailMovieBinding.tvDetailMovieGenres.text = "Generos: $movieGenres"
            fragmentDetailMovieBinding.tvDetailMovieDesc.text =  if(it.overview.isEmpty()) "No se encontro una descripcion" else "Descripcion: " + it.overview

            Picasso.get()
                .load(Constants().MOVIE_DB_IMAGE_URL + movie.posterPath)
                .placeholder(R.drawable.ic_no_image) // Placeholder while the image is loading
                .resize(600, 900)
                .centerCrop()
                .error(R.drawable.ic_no_image) // Error image if the loading fails
                .into(fragmentDetailMovieBinding.imageViewMovie)


            fragmentDetailMovieBinding.ivArrowBack.setOnClickListener {
                this.finish()
            }

        }

        detailMovieViewModel.errorMessage.observe(this) { errorMessage ->
            // Maneja el error, muestra un mensaje al usuario, etc.
        }
        detailMovieViewModel.getMovieDetail(Constants().MOVIE_DB_API_KEY,movie.id)
    }
}