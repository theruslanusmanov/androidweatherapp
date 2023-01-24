package com.theruslanusmanov.androidweatherapp.data.models

data class SearchCitiesItem(
    val AdministrativeArea: AdministrativeArea,
    val Country: Country,
    val DataSets: List<String>,
    val EnglishName: String,
    val GeoPosition: GeoPosition,
    val IsAlias: Boolean,
    val Key: String,
    val LocalizedName: String,
    val PrimaryPostalCode: String,
    val Rank: Int,
    val Region: Region,
    val SupplementalAdminAreas: List<SupplementalAdminArea>,
    val TimeZone: TimeZone,
    val Type: String,
    val Version: Int
)