package com.macropay.prueba.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.macropay.prueba.data.model.DetailMovieModel
import com.macropay.prueba.data.repositories.MovieRepository

class DetailMovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _dataList = MutableLiveData<DetailMovieModel>()
    val dataList: LiveData<DetailMovieModel> get() = _dataList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun getMovieDetail(apiKey:String,idMovie:Int) {
        repository.getMovieDetail(apiKey,idMovie) { data, error ->
            if (data != null) {
                _dataList.postValue(data)
            } else {
                _errorMessage.postValue(error)
            }
        }
    }
}