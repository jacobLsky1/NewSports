package com.jacoblip.andriod.newsports.data.models.seasons.players

import androidx.annotation.Keep

@Keep
class Penalty(
        val won: Int? = null,
        val scores: Int? = null,
        val missed: Int? = null,
        val committed: Int? = null,
        val saves: Int? = null
)