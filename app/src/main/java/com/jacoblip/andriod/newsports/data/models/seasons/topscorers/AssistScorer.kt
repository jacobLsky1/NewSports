package com.jacoblip.andriod.newsports.data.models.seasons.topscorers

import com.jacoblip.andriod.newsports.data.models.fixture.lineup.PlayerData
import com.jacoblip.andriod.newsports.data.models.fixture.team.TeamData


class AssistScorer(
    val position: Int,
    val season_id: Long,
    val player_id: Long,
    val team_id: Long,
    val team: TeamData,
    val stage_id: Long,
    val assists: Int,
    val type: String,
    val player: PlayerData
)