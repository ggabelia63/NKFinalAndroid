package com.example.countriesapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesapp.R
import com.example.countriesapp.data.model.CountryResponse

class CountryDetailsAdapter(
    private val countries: List<CountryResponse>,
    private val onOpenedCountryMap: (String) -> Unit
) :
    RecyclerView.Adapter<CountryDetailsAdapter.CountryDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryDetailsViewHolder {
        return CountryDetailsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.country_details_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountryDetailsViewHolder, position: Int) {
        holder.onBindCountryDetails()
    }

    override fun getItemCount(): Int = countries.size

    inner class CountryDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n", "WrongViewCast")
        fun onBindCountryDetails() {
            val countryResponse = countries[adapterPosition]
            with(itemView) {
                val costOfArmsImageView = findViewById<ImageView>(R.id.constOfArmsImageView)
                val flagImageView =
                    findViewById<ImageView>(com.example.countriesapp.R.id.flagImageView)
                val independentView =
                    findViewById<TextView>(com.example.countriesapp.R.id.independentView)
                val countryName = findViewById<TextView>(R.id.countryNameView)
                val capitalView = findViewById<TextView>(com.example.countriesapp.R.id.capitalView)
                val regionView = findViewById<TextView>(com.example.countriesapp.R.id.regionView)
                val populationView =
                    findViewById<TextView>(com.example.countriesapp.R.id.populationView)
                val areaView = findViewById<TextView>(com.example.countriesapp.R.id.areaView)
                val startOfWeekView =
                    findViewById<TextView>(com.example.countriesapp.R.id.startOfWeekView)
                val openGoogleMapBtn = findViewById<ImageButton>(R.id.openCountriesMap)

                com.bumptech.glide.Glide.with(context).load(countryResponse.coatOfArms.png)
                    .circleCrop()
                    .into(costOfArmsImageView)

                com.bumptech.glide.Glide.with(context).load(countryResponse.flags.png).circleCrop()
                    .into(flagImageView)

                with(countryResponse) {
                    if (independent) {
                        independentView.text = "Independent"
                        independentView.setTextColor(context.getColor(R.color.green))
                    } else {
                        independentView.text = "Not Independent"
                        independentView.setTextColor(context.getColor(R.color.red))
                    }
                    countryName.text = name.common
                    if (capital != null) {
                        capitalView.text = "Capital - ${capital[0]}"
                    }
                    regionView.text = "Region - $region"
                    populationView.text = "Population - $population"
                    areaView.text = "Area = $area"
                    startOfWeekView.text = "Start of week = $startOfWeek"

                    openGoogleMapBtn.setOnClickListener {
                        onOpenedCountryMap(countryResponse.maps.googleMaps)
                    }
                }
            }
        }
    }
}