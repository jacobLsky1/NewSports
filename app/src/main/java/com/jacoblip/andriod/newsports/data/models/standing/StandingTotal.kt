package com.jacoblip.andriod.newsports.data.models.standing

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class StandingTotal(
        val goal_difference: String,
        val points: Int
) : Serializable
