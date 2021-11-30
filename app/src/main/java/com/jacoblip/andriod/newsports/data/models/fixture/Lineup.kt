package com.jacoblip.andriod.newsports.data.models.fixture

import java.io.Serializable

data class Lineup(
    val starting_lineups: List<LineupPlayer>,
    val substitutes: List<LineupPlayer>,
    val coach: List<LineupPlayer>,
    val missing_players: List<LineupPlayer>
): Serializable