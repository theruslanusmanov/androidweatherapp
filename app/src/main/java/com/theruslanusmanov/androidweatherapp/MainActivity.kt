package com.theruslanusmanov.androidweatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theruslanusmanov.androidweatherapp.search.SearchView
import com.theruslanusmanov.androidweatherapp.search.SearchViewModel
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.weather.ForecastViewModel
import com.theruslanusmanov.androidweatherapp.weather.WeatherApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Forecast ViewModel
        val forecastViewModel: ForecastViewModel by viewModels()

        // Search ViewModel
        val searchViewModel: SearchViewModel by viewModels()

        // Location ViewModel
        val locationViewModel: LocationViewModel by viewModels()
        Log.d("LOCATION_VIEWMODEL_ACTIVITY", locationViewModel.currentLocation.value.toString())

        setContent {
            AndroidWeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black,
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = WeatherRoutes.Main.name
                    ) {
                        // * Main
                        composable(route = WeatherRoutes.Main.name) {
                            WeatherApp(forecastViewModel, navController)
                        }
                        // * Search
                        composable(route = WeatherRoutes.Search.name) {
                            SearchView(searchViewModel, navController)
                        }
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidWeatherAppTheme {
        WeatherApp({} as ForecastViewModel, {} as NavController)
    }
}