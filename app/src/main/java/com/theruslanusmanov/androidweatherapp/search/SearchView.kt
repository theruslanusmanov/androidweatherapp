package com.theruslanusmanov.androidweatherapp.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.theruslanusmanov.androidweatherapp.R
import com.theruslanusmanov.androidweatherapp.WeatherRoutes

@Composable
fun SearchView(searchViewModel: SearchViewModel, navController: NavController) {
    val searchResults by searchViewModel.searchState.observeAsState()
    searchResults?.let {
        Log.d("SEARCH", it.toString())
        Column {
            Header(title = "Search", navController = navController)
            for (index in 0..searchResults!!.results.size - 1) {
                Row {
                    Text(text = searchResults!!.results[index].country.toString(), color = Color.White)
                    Text(text = searchResults!!.results[index].name.toString(), color = Color.White)
                    Text(text = searchResults!!.results[index].latitude.toString(), color = Color.White)
                    Text(text = searchResults!!.results[index].longitude.toString(), color = Color.White)
                }
            }
        }
    }
}

@Composable
fun Header(title: String, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Search Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    navController.navigate(WeatherRoutes.Main.name)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search icon",
                tint = Color.White
            )
        }
        Text(
            text = title.uppercase(),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 32.sp,
                color = Color.White,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}