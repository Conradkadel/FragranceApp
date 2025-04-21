package com.example.fragranceapp.network

import com.example.fragranceapp.model.Fragrance
import retrofit2.http.GET

interface FragranceApi {
    @GET("/")    // or your endpoint path
    suspend fun getFragrances(): List<Fragrance>
}

object ApiClient {
    private const val BASE_URL = "https://fragranceapi-aa634fd3f236.herokuapp.com/"
    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .build()

    val service: FragranceApi = retrofit.create(FragranceApi::class.java)
}