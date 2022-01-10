package com.jacoblip.andriod.newsports.data.models.fixture.time

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class StartingAt(
    val date_time: String,
    val date: String,
    val time: String,
    val timezone: String,
    val timestamp: Long
): Serializable