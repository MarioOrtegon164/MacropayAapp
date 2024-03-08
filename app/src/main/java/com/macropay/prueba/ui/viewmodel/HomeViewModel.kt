package com.macropay.prueba.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.macropay.prueba.data.model.MovieModel
import com.macropay.prueba.data.repositories.MovieRepository

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _dataList = MutableLiveData<MovieModel>()
    val dataList: LiveData<MovieModel> get() = _dataList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Lógica de navegación utilizando LiveData
    private val _navigateToDetailMovieFragment = MutableLiveData<Boolean>()

    val navigateToDetailMovieFragment: LiveData<Boolean>
        get() = _navigateToDetailMovieFragment

    private val _navigateToHomeFragment = MutableLiveData<Boolean>()


    fun onNavigateToDetailMovieFragment() {
        _navigateToDetailMovieFragment.value = true
    }

    fun onNavigationToDetailMovieFragmentComplete() {
        _navigateToDetailMovieFragment.value = false
    }


    fun getMovies(apiKey:String,page:Int) {
        repository.getMovies(apiKey,page) { data, error ->
            if (data != null) {
                _dataList.postValue(data)
            } else {
                _errorMessage.postValue(error)
            }
        }
    }
}
