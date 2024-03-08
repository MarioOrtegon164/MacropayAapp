package com.macropay.prueba.core

import com.macropay.prueba.data.network.MovieApiClient
import com.macropay.prueba.data.network.MovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): MovieService {
        /*
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MovieApiClient::class.java)

         */

        val retrofit: MovieService by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }

        return retrofit

    }
}