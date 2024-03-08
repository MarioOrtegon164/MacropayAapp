package com.macropay.prueba.data.network

import com.macropay.prueba.data.model.DetailMovieModel
import com.macropay.prueba.data.model.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /*
    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieModel

     */

    @GET("movie/now_playing")
    fun getMovies(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int,
    @Query("language") language: String
    ): Call<MovieModel>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<DetailMovieModel>
}
