package com.theruslanusmanov.androidweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theruslanusmanov.androidweatherapp.search.SearchView
import com.theruslanusmanov.androidweatherapp.weather.WeatherApp
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.weather.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val forecastViewModel: ForecastViewModel by viewModels()

        setContent {

            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = WeatherRoutes.Main.name
            ) {
                // * Main
                composable(route = WeatherRoutes.Main.name) {
                    WeatherApp(forecastViewModel)
                }
                // * Search
                composable(route = WeatherRoutes.Search.name) {
                    SearchView()
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidWeatherAppTheme {
        WeatherApp({} as ForecastViewModel)
    }
}