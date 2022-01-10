package com.jacoblip.andriod.newsports.data.models.fixture.color

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class Color(
        val localteam: TeamColor,
        val visitorteam: TeamColor
) : Serializable