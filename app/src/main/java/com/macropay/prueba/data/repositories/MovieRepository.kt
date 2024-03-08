package com.macropay.prueba.data.repositories

import com.macropay.prueba.data.model.MovieModel
import com.macropay.prueba.data.network.MovieService

class MovieRepository(private val movieApiService: MovieService) {

    suspend fun getMovies(apiKey: String, page: Int): MovieModel {
        return movieApiService.getMovies(apiKey, page)
    }
}
