package com.example.dvtweatherapp.utils

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.dvtweatherapp.R
import java.util.Calendar
import java.util.Date

object FlowConstants {
    const val API_KEY = "6e57c12676116706af4848cf8663e0fc"

    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val Poppins = FontFamily(
        Font(googleFont = GoogleFont("Poppins"), fontProvider = provider, weight = FontWeight.Normal),
        Font(googleFont = GoogleFont("Poppins"), fontProvider = provider, weight = FontWeight.Bold),
        Font(googleFont = GoogleFont("Poppins"), fontProvider = provider, weight = FontWeight.SemiBold)
    )

    fun getHour(unixTimestamp: Long): Int {
        val date = Date(unixTimestamp * 1000)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    fun kelvinToCelsius(kelvin: Double): Int {
        return (kelvin - 273.15).toInt()
    }

    fun kelvinToFahrenheit(kelvin: Double): Int {
        return ((kelvin - 273.15) * 9/5 + 32).toInt()
    }
}