package com.theruslanusmanov.androidweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
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
import com.theruslanusmanov.androidweatherapp.weather.DefaultWeatherViewModel
import com.theruslanusmanov.androidweatherapp.weather.WeatherView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
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
                        startDestination = WeatherRoutes.Main
                    ) {
                        // * Main
                        composable<WeatherRoutes.Main> {
                            WeatherView(onSearch = {navController.navigate(WeatherRoutes.Search)})
                        }
                        // * Search
                        composable<WeatherRoutes.Search> {
                            SearchView(onBack = {navController.navigate(WeatherRoutes.Main)})
                        }
                    }
                }
            }

        }
    }
}
