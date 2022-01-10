package com.jacoblip.andriod.newsports.data.models.fixture.weather

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class Coordinate(
        val lat: Double,
        val lon: Double
) : Serializable