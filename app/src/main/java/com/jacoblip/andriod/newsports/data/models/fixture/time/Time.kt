package com.jacoblip.andriod.newsports.data.models.fixture.time

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.fixture.time.StartingAt
import java.io.Serializable
@Keep
class Time(
    val status: String,
    val starting_at: StartingAt,
    val minute: Int,
    val second: Int? = null,
    val added_time: Int? = null,
    val extra_minute: Int? = null,
    val injury_time: Int? = null
) : Serializable