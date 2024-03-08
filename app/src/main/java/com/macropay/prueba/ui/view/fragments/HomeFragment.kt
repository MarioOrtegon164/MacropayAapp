package com.macropay.prueba.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.macropay.prueba.R
import com.macropay.prueba.data.model.Movie
import com.macropay.prueba.data.repositories.MyRepository
import com.macropay.prueba.databinding.FragmentHomeBinding
import com.macropay.prueba.ui.adapters.MovieAdapter
import com.macropay.prueba.ui.viewmodel.HomeViewModel
import com.macropay.prueba.ui.viewmodel.MyViewModelFactory

class HomeFragment : Fragment() ,MovieAdapter.OnItemClickListener{

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = MyRepository()
        val viewModelFactory = MyViewModelFactory(repository)

        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val apiKey = "c0823934438075d63f1dbda4023e76fc"
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)

        homeViewModel.dataList.observe(viewLifecycleOwner) {
            // Actualiza la interfaz de usuario con la lista de datos
            val recyclerView = homeBinding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            movieAdapter = MovieAdapter(it.movies,this)
            recyclerView.adapter = movieAdapter
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner, { errorMessage ->
            // Maneja el error, muestra un mensaje al usuario, etc.
        })
        homeViewModel.getMovies(apiKey,1)

        return homeBinding.root
    }

    override fun onItemClick(movie: Movie) {

        val gson = Gson()
        val json = gson.toJson(movie)
        //Toast.makeText(context, json.toString(), Toast.LENGTH_LONG).show()

        val bundle = bundleOf("movie" to json.toString())
        findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment,bundle)
    }
}