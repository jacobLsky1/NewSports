package com.jacoblip.andriod.newsports.data.models.fixture

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class Statistic(
    val type: String,
    val home: String,
    val away: String
): Serializable