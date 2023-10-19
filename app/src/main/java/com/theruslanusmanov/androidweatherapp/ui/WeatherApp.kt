package com.theruslanusmanov.androidweatherapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theruslanusmanov.androidweatherapp.ui.theme.AndroidWeatherAppTheme
import com.theruslanusmanov.androidweatherapp.ui.theme.fontFamily


@Composable
fun WeatherApp() {
    AndroidWeatherAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black,
        ) {
            Weather()
        }
    }
}

@Composable
fun Weather() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
        LocationName(name = "Kazan")
        Temperature(value = 23)
        WeatherShortText(value = "Cloudy")
        Spacer(modifier = Modifier.height(40.dp))
        TenDayForecast()
    }
}

@Composable
fun LocationName(name: String) {
    Text(
        text = name.uppercase(),
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 32.sp,
            color = Color.White,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )
}

@Composable
fun Temperature(value: Int) {
    Text(
        text = "$value°",
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 120.sp,
            color = Color.White,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        modifier = Modifier.absoluteOffset(22.dp)
    )
}

@Composable
fun WeatherShortText(value: String) {
    Text(
        text = value,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 32.sp,
            color = Color.White,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )
}

@Composable
fun TenDayForecast() {
    Column(
        Modifier
            .height(240.dp)
            .fillMaxWidth()
            .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .padding(10.dp)
    ) {
        Text(text = "10-Day Forecast".uppercase(), fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Preview()
@Composable
fun WeatherAppPreview() {
    Weather()
}