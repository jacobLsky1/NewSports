package com.jacoblip.andriod.newsports.data.models.fixture.round

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Round(
        val id: Long,
        val name: Int,
        val league_id: Long,
        val season_id: Long,
        val stage_id: Long,
        val start: String,
        val end: String
) : Serializable