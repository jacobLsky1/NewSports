package com.jacoblip.andriod.newsports.data.models.fixture

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class Standing(
    val localteam_position: Int,
    val visitorteam_position: Int
): Serializable