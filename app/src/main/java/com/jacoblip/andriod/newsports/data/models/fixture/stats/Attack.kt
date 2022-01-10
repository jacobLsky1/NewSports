package com.jacoblip.andriod.newsports.data.models.fixture.stats

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class Attack(
        val attacks: Int,
        val dangerous_attacks: Int
) : Serializable