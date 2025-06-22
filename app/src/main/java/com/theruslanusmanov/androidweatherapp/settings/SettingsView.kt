package com.theruslanusmanov.androidweatherapp.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsView() {
    Text(text = "Settings", color = Color.White)
}

@Preview(name = "Weather screen")
@Composable
fun SearchViewPreview() {
    SettingsView()
}