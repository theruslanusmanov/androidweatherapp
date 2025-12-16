package com.theruslanusmanov.androidweatherapp

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theruslanusmanov.androidweatherapp.search.SearchView
import com.theruslanusmanov.androidweatherapp.search.SearchViewModel
import com.theruslanusmanov.androidweatherapp.settings.SettingsView
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.weather.WeatherViewModel
import com.theruslanusmanov.androidweatherapp.weather.WeatherView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Forecast ViewModel
        val weatherViewModel: WeatherViewModel by viewModels()

        // Search ViewModel
        val searchViewModel: SearchViewModel by viewModels()

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
                        startDestination = WeatherRoutes.Main.name
                    ) {
                        // * Main
                        composable(route = WeatherRoutes.Main.name) {
                            WeatherView(weatherViewModel, navController)
                        }
                        // * Search
                        composable(route = WeatherRoutes.Search.name) {
                            SearchView(searchViewModel, navController)
                        }
                        // * Settings
                        composable(route = WeatherRoutes.Settings.name) {
                            SettingsView()
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
        WeatherView({} as WeatherViewModel, {} as NavController)
    }
}