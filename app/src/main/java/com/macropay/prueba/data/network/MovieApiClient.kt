package com.macropay.prueba.data.network

import com.macropay.prueba.data.model.MovieModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiClient {
    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieModel

}