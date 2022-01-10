package com.jacoblip.andriod.newsports.data.models.fixture

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.fixture.Lineup
import java.io.Serializable
@Keep
data class LineupEnvelop(
    val home: Lineup,
    val away: Lineup
):Serializable