package com.theruslanusmanov.androidweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.theruslanusmanov.androidweatherapp.main.WeatherApp
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherApp()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidWeatherAppTheme {
        WeatherApp()
    }
}