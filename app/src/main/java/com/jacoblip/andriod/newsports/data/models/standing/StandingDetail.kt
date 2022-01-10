package com.jacoblip.andriod.newsports.data.models.standing

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Keep
class StandingDetail(
        val games_played: Int,
        val won: Int,
        val draw: Int,
        val lost: Int,
        val goals_scored: Int,
        val goals_against: Int
) : Serializable {
}
