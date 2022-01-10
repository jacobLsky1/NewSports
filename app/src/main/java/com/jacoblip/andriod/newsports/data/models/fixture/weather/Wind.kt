package com.jacoblip.andriod.newsports.data.models.fixture.weather

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class Wind(
    val speed: String,
    val degree: Int
): Serializable