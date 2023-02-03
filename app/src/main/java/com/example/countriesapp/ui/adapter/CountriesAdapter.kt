package com.example.countriesapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countriesapp.R
import com.example.countriesapp.data.model.CountryEntity

class CountriesAdapter(
    private val countries: List<CountryEntity>,
    private val onCountrySelected: (String) -> Unit
) :
    RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.country_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.onBindCountry()
    }

    override fun getItemCount(): Int = countries.size

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun onBindCountry() {
            val regionTitle = itemView.findViewById<TextView>(R.id.regionTitleView)
            val countryTitle = itemView.findViewById<TextView>(R.id.countryTitleView)
            val flagImage = itemView.findViewById<ImageView>(R.id.flagImageView)

            val country = countries[adapterPosition]
            regionTitle.text = "Region ${country.region}"
            countryTitle.text = country.countryName
            Glide.with(itemView.context).load(country.flag).into(flagImage)

            itemView.rootView.setOnClickListener {
                onCountrySelected(country.countryName)
            }
        }
    }
}