package com.jacoblip.andriod.newsports.data.models.seasons.topscorers

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.fixture.lineup.PlayerData
import com.jacoblip.andriod.newsports.data.models.fixture.team.TeamData

@Keep
class GoalScorer(
    val position: Int,
    val season_id: Long,
    val player_id: Long,
    val team_id: Long,
    val team: TeamData,
    val stage_id: Long,
    val goals: Int,
    val penalty_goals: Int,
    val player: PlayerData
)