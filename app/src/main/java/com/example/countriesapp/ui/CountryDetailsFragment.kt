package com.example.countriesapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesapp.R
import com.example.countriesapp.data.CountriesApiService
import com.example.countriesapp.data.RetrofitClient
import com.example.countriesapp.data.model.CountryResponse
import com.example.countriesapp.ui.adapter.CountryDetailsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryDetailsFragment : BaseFragment() {

    private lateinit var countryDetailsRecyclerView: RecyclerView

    override fun getLayout() = R.layout.country_details_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCountryDetails()
    }

    private fun loadCountryDetails() {
        countryDetailsRecyclerView = requireView().findViewById(R.id.countryDetailsRecyclerView)
        val countryName = arguments?.getString("Country Name")
        RetrofitClient.getRetrofit().create(CountriesApiService::class.java)
            .getCountryDetails(countryName!!)
            .enqueue(object : Callback<List<CountryResponse>> {
                override fun onResponse(
                    call: Call<List<CountryResponse>>,
                    response: Response<List<CountryResponse>>
                ) {
                    if (response.isSuccessful) {
                        initCountryDetailsAdapter(response.body()!!)
                    } else {
                        showToastMessage(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<List<CountryResponse>>, t: Throwable) {
                    showToastMessage(t.toString())
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun initCountryDetailsAdapter(countries: List<CountryResponse>) {
        countryDetailsRecyclerView.adapter = CountryDetailsAdapter(countries) {
            findNavController().navigate(
                R.id.action_countryDetailsFragment_to_mapFragment,
                bundleOf(MapFragment.GOOGLE_MAP_LINK to it)
            )
        }
        countryDetailsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}