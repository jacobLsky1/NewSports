package com.jacoblip.andriod.newsports.data.models.seasons.topscorers

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.fixture.lineup.PlayerData
import com.jacoblip.andriod.newsports.data.models.fixture.team.TeamData

@Keep
class CardScorer(
    val position: Int,
    val season_id: Long,
    val player_id: Long,
    val team_id: Long,
    val stage_id: Long,
    val team: TeamData,
    val yellowcards: Int,
    val redcards: Int,
    val type: String,
    val player: PlayerData
)