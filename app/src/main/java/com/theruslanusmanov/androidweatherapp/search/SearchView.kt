package com.theruslanusmanov.androidweatherapp.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.theruslanusmanov.androidweatherapp.R
import com.theruslanusmanov.androidweatherapp.WeatherRoutes

@Composable
fun SearchView(searchViewModel: SearchViewModel, navController: NavController) {
    val searchResults by searchViewModel.searchState.observeAsState()

    Column(modifier = Modifier.padding(20.dp)) {
        if (searchResults?.results?.isEmpty() == true) {
            Text(text = "LOADING...", color = Color.White)
        } else {
            searchResults?.let {
                Log.d("SEARCH", it.toString())
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Header(title = "Search", navController = navController)
                    for (index in 0 until searchResults!!.results.size) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(48.dp)
                                .fillMaxWidth()
                                .padding(8.dp, 2.dp)
                                .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                                .background(Color.DarkGray)
                                .clickable {
                                    navController.navigate(WeatherRoutes.Main.name)
                                }
                        ) {
                            Text(
                                text = searchResults!!.results[index].country.toString(),
                                color = Color.White,
                                modifier = Modifier.padding(start = 48.dp)
                            )
                            Text(
                                text = searchResults!!.results[index].name.toString(),
                                color = Color.White,
                                modifier = Modifier.padding(end = 48.dp)
                            )
                        }
                    }
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
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Search icon",
                tint = Color.White
            )
        }
        TextField(
            value = "Search...", onValueChange = { }, textStyle = TextStyle(
                fontSize = 32.sp,
                color = Color.White,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
    }
}