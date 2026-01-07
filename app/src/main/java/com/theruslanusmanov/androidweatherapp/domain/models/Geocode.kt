@file:OptIn(ExperimentalSerializationApi::class)

package com.theruslanusmanov.androidweatherapp.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys


// API https://geocoding-api.open-meteo.com/v1/search?name=Berlin&count=10&language=en&format=json
@Serializable
@JsonIgnoreUnknownKeys
data class Geocode (
    @SerialName("results"           ) var results          : ArrayList<GeocodeResults> = arrayListOf(),
    @SerialName("generationtime_ms" ) var generationtimeMs : Double?            = null
)

@Serializable
@JsonIgnoreUnknownKeys
data class GeocodeResults (
    @SerialName("id"           ) var id          : Int?              = null,
    @SerialName("name"         ) var name        : String?           = null,
    @SerialName("latitude"     ) var latitude    : Double?           = null,
    @SerialName("longitude"    ) var longitude   : Double?           = null,
    @SerialName("elevation"    ) var elevation   : Double?              = null,
    @SerialName("feature_code" ) var featureCode : String?           = null,
    @SerialName("country_code" ) var countryCode : String?           = null,
    @SerialName("admin1_id"    ) var admin1Id    : Int?              = null,
    @SerialName("admin3_id"    ) var admin3Id    : Int?              = null,
    @SerialName("admin4_id"    ) var admin4Id    : Int?              = null,
    @SerialName("timezone"     ) var timezone    : String?           = null,
    @SerialName("population"   ) var population  : Int?              = null,
    @SerialName("postcodes"    ) var postcodes   : ArrayList<String> = arrayListOf(),
    @SerialName("country_id"   ) var countryId   : Int?              = null,
    @SerialName("country"      ) var country     : String?           = null,
    @SerialName("admin1"       ) var admin1      : String?           = null,
    @SerialName("admin3"       ) var admin3      : String?           = null,
    @SerialName("admin4"       ) var admin4      : String?           = null
)