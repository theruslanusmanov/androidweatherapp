package com.theruslanusmanov.androidweatherapp.ui.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import com.theruslanusmanov.androidweatherapp.R


@OptIn(ExperimentalTextApi::class)
val interFontFamily =
    FontFamily(
        Font(
            R.font.inter,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(700),
                FontVariation.slant(0f),
            )
        ),
    )