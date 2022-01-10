package com.jacoblip.andriod.newsports.data.models.fixture

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class Card(
    val time: String,
    val home_fault: String,
    val card: String,
    val away_fault: String
): Serializable