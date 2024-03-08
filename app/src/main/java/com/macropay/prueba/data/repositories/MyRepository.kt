package com.macropay.prueba.data.repositories

import com.macropay.prueba.core.RetrofitClient
import com.macropay.prueba.data.model.DetailMovieModel
import com.macropay.prueba.data.model.MovieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRepository {

     fun getMovies(apikey:String,page:Int,callback: (MovieModel?, String?) -> Unit) {
        val call = RetrofitClient.instance.getMovies(apikey,page,"es-MX")

        call.enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Error en la respuesta de la API")
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }

    fun getMovieDetail(apikey:String,idMovie:Int,callback: (DetailMovieModel?, String?) -> Unit) {
        val call = RetrofitClient.instance.getMovieDetail(idMovie,apikey,"es-MX")

        call.enqueue(object : Callback<DetailMovieModel> {
            override fun onResponse(call: Call<DetailMovieModel>, response: Response<DetailMovieModel>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Error en la respuesta de la API")
                }
            }

            override fun onFailure(call: Call<DetailMovieModel>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }

}
