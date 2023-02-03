package com.example.countriesapp.data.model

data class CountryResponse(
    val name: NameInfo,
    val capital: List<String>,
    val region: String,
    val latlng: List<Double>,
    val independent : Boolean,
    val subregion : String,
    val area : Double,
    val maps : Map,
    val population : Long,
    val flags: Flag,
    val coatOfArms : CostOfArms,
    val startOfWeek : String
)

data class NameInfo(
    val common: String,
    val official: String,
    val independent: Boolean,
)

data class Flag(val png: String)

data class CostOfArms(val png : String)

data class Map(val googleMaps : String)