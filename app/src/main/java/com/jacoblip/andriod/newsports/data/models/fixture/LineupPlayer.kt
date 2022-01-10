package com.jacoblip.andriod.newsports.data.models.fixture

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class LineupPlayer(
    val lineup_player: String,
    val lineup_number: String,
    val lineup_position: String
): Serializable