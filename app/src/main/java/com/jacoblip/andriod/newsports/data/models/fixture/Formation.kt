package com.jacoblip.andriod.newsports.data.models.fixture

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class Formation(
        val localteam_formation: String,
        val visitorteam_formation: String
) : Serializable