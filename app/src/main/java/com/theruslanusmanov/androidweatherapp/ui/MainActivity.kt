package com.theruslanusmanov.androidweatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.ui.theme.fontFamily
import com.theruslanusmanov.androidweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentWeather: WeatherViewModel by viewModels()

        setContent {
            WeatherApp(currentWeather = currentWeather)
        }
    }
}

@Composable
fun LoadingState() {
    Text(
        text = "Loading...",
        style = TextStyle(
            fontSize = 32.sp,
            color = Color.White
        )
    )
}

@Composable
fun City(name: String) {
    Text(
        text = name,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 32.sp,
            color = Color.White
        )
    )
}

@Composable
fun Temperature(value: String) {
    Text(
        text = "$value°",
        fontFamily = fontFamily,
        fontWeight = FontWeight.W900,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 72.sp,
            color = Color.White
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidWeatherAppTheme {
        Temperature("-10")
    }
}