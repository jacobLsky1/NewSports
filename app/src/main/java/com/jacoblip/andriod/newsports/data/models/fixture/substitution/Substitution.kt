package com.jacoblip.andriod.newsports.data.models.fixture.substitution

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class Substitution(
        val id: Long,
        val team_id: String,
        val type: String,
        val fixture_id: Long,
        val player_in_id: Long,
        val player_out_id: Long,
        val player_in_name: String,
        val player_out_name: String,
        val minute: Int,
        val extra_minute: Int? = null,
        val injuried: Boolean? = null
): Serializable