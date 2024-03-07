package com.macropay.prueba.data.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("quote") val quote: String,
    @SerializedName("author") val author: String
)