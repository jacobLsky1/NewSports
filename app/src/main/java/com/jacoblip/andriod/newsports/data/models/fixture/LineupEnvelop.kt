package com.jacoblip.andriod.newsports.data.models.fixture

import com.jacoblip.andriod.newsports.data.models.fixture.Lineup
import java.io.Serializable

data class LineupEnvelop(
    val home: Lineup,
    val away: Lineup
):Serializable