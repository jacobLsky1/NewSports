package com.jacoblip.andriod.newsports.data.models.fixture.stats

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class Pass(
        val total: Int,
        val accurate: Int,
        val percentage: Int
) : Serializable