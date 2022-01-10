package com.jacoblip.andriod.newsports.data.models.fixture.stats

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class Shot(
        val total: Int,
        val ongoal: Int,
        val offgoal: Int,
        val insidebox: Int,
        val outsidebox: Int
) : Serializable