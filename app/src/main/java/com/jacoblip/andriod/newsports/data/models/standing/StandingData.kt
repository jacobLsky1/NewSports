package com.jacoblip.andriod.newsports.data.models.standing

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class StandingData(
        val data: ArrayList<TeamStanding>
) : Serializable
