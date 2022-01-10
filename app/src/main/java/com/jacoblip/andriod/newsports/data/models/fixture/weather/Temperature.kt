package com.jacoblip.andriod.newsports.data.models.fixture.weather

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class Temperature(
    val temp: Double,
    val unit: String
): Serializable