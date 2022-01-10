package com.jacoblip.andriod.newsports.data.models.fixture.weather

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.fixture.weather.Temperature
import java.io.Serializable
@Keep
class WeatherReport(
    val code: String,
    val type: String,
    val icon: String,
    val temperature: Temperature,
    val temperature_celcius: Temperature,
    val clouds: String,
    val humidity: String,
    val pressure: Double,
    val updated_at: String
) : Serializable