package com.theruslanusmanov.androidweatherapp.common

import android.content.Context
import kotlinx.serialization.json.Json
import java.io.IOException

object AssetUtils {
    inline fun <reified T> loadJsonFromAssets(context: Context, resourceId: Int): T? {
        val jsonString: String
        try {
            jsonString = context.resources.getString(resourceId)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return Json.decodeFromString<T>(jsonString)
    }
}