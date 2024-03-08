package com.macropay.prueba.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.macropay.prueba.data.model.Movie
import com.macropay.prueba.data.repositories.MyRepository
import com.macropay.prueba.databinding.ActivityHomeBinding
import com.macropay.prueba.ui.adapters.MovieAdapter
import com.macropay.prueba.ui.viewmodel.HomeViewModel
import com.macropay.prueba.ui.viewmodel.MyViewModelFactory
import com.macropay.prueba.utils.Constants

class HomeActivity : AppCompatActivity(),MovieAdapter.OnItemClickListener {

    private lateinit var homeBinding: ActivityHomeBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userEmail:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        val repository = MyRepository()
        val viewModelFactory = MyViewModelFactory(repository)

        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        userEmail = intent.getStringExtra("email").toString()

        setupObservers()
        init()

        setContentView(homeBinding.root)
    }

    private fun setupObservers() {
        homeViewModel.dataList.observe(this) {
            val recyclerView = homeBinding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(this)
            movieAdapter = MovieAdapter(it.movies,this)
            recyclerView.adapter = movieAdapter
        }

        homeViewModel.errorMessage.observe(this, { errorMessage ->
            // Maneja el error, muestra un mensaje al usuario, etc.
        })
    }

    private fun init() {
        homeBinding.tvUserEmail.text = userEmail

        homeBinding.ivCloseSession.setOnClickListener {
            showCloseSessionMessage()
        }

        homeViewModel.getMovies(Constants().MOVIE_DB_API_KEY,1)
    }

    override fun onItemClick(movie: Movie) {

        val gson = Gson()
        val json = gson.toJson(movie)

        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("movie", json)
        startActivity(intent)
    }

    private fun showCloseSessionMessage() {

        val sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog.titleText = "Desea cerrar sesion?"
        sweetAlertDialog.setConfirmButton("Si") {
            sweetAlertDialog.dismissWithAnimation()
            navigateToMainActivity()
        }
        sweetAlertDialog.setCancelButton("No") {
            sweetAlertDialog.dismissWithAnimation()
        }
        sweetAlertDialog.show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}