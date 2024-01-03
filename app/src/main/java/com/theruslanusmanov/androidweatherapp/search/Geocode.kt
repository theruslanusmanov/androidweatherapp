package com.theruslanusmanov.androidweatherapp.search

import com.google.gson.annotations.SerializedName


// API https://geocoding-api.open-meteo.com/v1/search?name=Berlin&count=10&language=en&format=json
data class Geocode (
    @SerializedName("results"           ) var results          : ArrayList<GeocodeResults> = arrayListOf(),
    @SerializedName("generationtime_ms" ) var generationtimeMs : Double?            = null
)

data class GeocodeResults (
    @SerializedName("id"           ) var id          : Int?              = null,
    @SerializedName("name"         ) var name        : String?           = null,
    @SerializedName("latitude"     ) var latitude    : Double?           = null,
    @SerializedName("longitude"    ) var longitude   : Double?           = null,
    @SerializedName("elevation"    ) var elevation   : Int?              = null,
    @SerializedName("feature_code" ) var featureCode : String?           = null,
    @SerializedName("country_code" ) var countryCode : String?           = null,
    @SerializedName("admin1_id"    ) var admin1Id    : Int?              = null,
    @SerializedName("admin3_id"    ) var admin3Id    : Int?              = null,
    @SerializedName("admin4_id"    ) var admin4Id    : Int?              = null,
    @SerializedName("timezone"     ) var timezone    : String?           = null,
    @SerializedName("population"   ) var population  : Int?              = null,
    @SerializedName("postcodes"    ) var postcodes   : ArrayList<String> = arrayListOf(),
    @SerializedName("country_id"   ) var countryId   : Int?              = null,
    @SerializedName("country"      ) var country     : String?           = null,
    @SerializedName("admin1"       ) var admin1      : String?           = null,
    @SerializedName("admin3"       ) var admin3      : String?           = null,
    @SerializedName("admin4"       ) var admin4      : String?           = null
)