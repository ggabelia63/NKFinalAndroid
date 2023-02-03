package com.example.countriesapp.data

import com.example.countriesapp.data.model.CountryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {
    @GET("all")
    fun getAllCountry(): Call<List<CountryResponse>>

    @GET("name/{name}")
    fun getCountryDetails(@Path("name") name: String): Call<List<CountryResponse>>
}