package com.jacoblip.andriod.newsports.data.models.seasons.players

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.seasons.players.PlayerRanking
import java.io.Serializable

@Keep
class SquadData(var data: ArrayList<PlayerRanking>) : Serializable