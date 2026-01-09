package com.theruslanusmanov.androidweatherapp.search

import com.theruslanusmanov.androidweatherapp.domain.models.Geocode

sealed interface SearchViewState {
    data object Loading : SearchViewState
    data object Empty : SearchViewState
    data class Success(val data: Geocode?) : SearchViewState
}