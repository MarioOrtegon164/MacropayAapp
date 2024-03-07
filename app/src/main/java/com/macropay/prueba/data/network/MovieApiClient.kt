package com.macropay.prueba.data.network

import com.macropay.prueba.data.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiClient {
    @GET("3/movie/now_playing")
    suspend fun getAllQuotes(): Response<List<MovieModel>>
}