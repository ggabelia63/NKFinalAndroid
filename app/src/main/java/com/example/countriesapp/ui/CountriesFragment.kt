package com.example.countriesapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.countriesapp.App
import com.example.countriesapp.R
import com.example.countriesapp.data.CountriesApiService
import com.example.countriesapp.data.RetrofitClient
import com.example.countriesapp.data.model.CountryEntity
import com.example.countriesapp.data.model.CountryResponse
import com.example.countriesapp.ui.adapter.CountriesAdapter
import com.example.countriesapp.worker.UpdateCountriesWorker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class CountriesFragment : BaseFragment() {

    private lateinit var countriesRecyclerView: RecyclerView
    private lateinit var loadingProgressBar : ProgressBar
    private val databaseHelper = App.getAppInstance()!!.databaseHelper!!

    override fun getLayout() = R.layout.fragment_countries

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countriesRecyclerView = requireView().findViewById(R.id.countriesRecyclerView)
        loadingProgressBar = requireView().findViewById(R.id.loadingProgressBar)

        val updateCountriesRequest =
            PeriodicWorkRequestBuilder<UpdateCountriesWorker>(1, TimeUnit.HOURS).build()
        WorkManager.getInstance(requireContext()).enqueue(updateCountriesRequest)

        if (databaseHelper.getCountries().isEmpty()) {
            loadCountriesFromApi()
        } else {
            initCountriesAdapter(databaseHelper.getCountries())
        }
    }

    private fun loadCountriesFromApi() {
        RetrofitClient.getRetrofit().create(CountriesApiService::class.java).getAllCountry()
            .enqueue(object : Callback<List<CountryResponse>> {
                override fun onResponse(
                    call: Call<List<CountryResponse>>,
                    response: Response<List<CountryResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()!!.forEach {
                            databaseHelper.insertCountry(response.body()!!)
                            initCountriesAdapter(databaseHelper.getCountries())
                        }
                    } else {
                        showToastMessage(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<List<CountryResponse>>, t: Throwable) {
                    showToastMessage(t.toString())
                }
            })
    }

    private fun initCountriesAdapter(countries: List<CountryEntity>) {
        loadingProgressBar.visibility = View.GONE
        countriesRecyclerView.visibility = View.VISIBLE
        countriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        countriesRecyclerView.adapter = CountriesAdapter(countries) {
            findNavController().navigate(
                R.id.action_countriesFragment_to_countryDetailsFragment, bundleOf(
                    "Country Name" to it
                )
            )
        }
    }
}

