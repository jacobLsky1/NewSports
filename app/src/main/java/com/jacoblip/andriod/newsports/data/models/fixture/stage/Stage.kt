package com.jacoblip.andriod.newsports.data.models.fixture.stage

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Stage(
        val id: Long,
        val name: String,
        val type: String,
        val league_id: Long,
        val season_id: Long,
        val sort_order: Int,
        val has_standings: Boolean
) : Serializable