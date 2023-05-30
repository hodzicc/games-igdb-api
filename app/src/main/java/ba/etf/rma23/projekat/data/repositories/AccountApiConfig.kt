package ba.etf.rma23.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AccountApiConfig {
    val retrofit : IGDBApi = Retrofit.Builder()
        .baseUrl("https://rma23ws.onrender.com/api-docs/#/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IGDBApi::class.java)
}