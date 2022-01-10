package com.jacoblip.andriod.newsports.data.models.team.transfers

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.fixture.lineup.PlayerData
import com.jacoblip.andriod.newsports.data.models.fixture.team.TeamData
import java.io.Serializable
@Keep
class Transfer(
    val player_id: Long,
    val from_team_id: Long,
    val to_team_id: Long,
    val season_id: Long,
    val transfer: String,
    val type: String,
    val date: String,
    val amount: String? = null,
    val player: PlayerData,
    val team: TeamData
) : Serializable