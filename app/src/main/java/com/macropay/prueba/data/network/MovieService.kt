package com.macropay.prueba.data.network

import com.macropay.prueba.data.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieService @Inject constructor(private val api: MovieApiClient) {

    suspend fun getMovies(apikey:String,page:Int): MovieModel {
        return withContext(Dispatchers.IO) {
            val response = api.getMovies(apikey,page)
            response
        }
    }

    //getMovies(apiKey, page)

}