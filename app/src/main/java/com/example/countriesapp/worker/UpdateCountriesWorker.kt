package com.example.countriesapp.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.countriesapp.App
import com.example.countriesapp.data.CountriesApiService
import com.example.countriesapp.data.RetrofitClient
import com.example.countriesapp.data.model.CountryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateCountriesWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        RetrofitClient.getRetrofit().create(CountriesApiService::class.java).getAllCountry()
            .enqueue(object : Callback<List<CountryResponse>> {
                override fun onResponse(
                    call: Call<List<CountryResponse>>,
                    response: Response<List<CountryResponse>>
                ) {
                    RetrofitClient.getRetrofit().create(CountriesApiService::class.java)
                        .getAllCountry()
                        .enqueue(object : Callback<List<CountryResponse>> {
                            override fun onResponse(
                                call: Call<List<CountryResponse>>,
                                response: Response<List<CountryResponse>>
                            ) {
                                if (response.isSuccessful) {
                                    val databaseHelper = App.getAppInstance()!!.databaseHelper!!
                                    databaseHelper.insertCountry(response.body()!!)
                                }
                            }
                            override fun onFailure(
                                call: Call<List<CountryResponse>>,
                                t: Throwable
                            ) {}
                        })
                }

                override fun onFailure(call: Call<List<CountryResponse>>, t: Throwable) {
                }
            })

        return Result.success()
    }

}