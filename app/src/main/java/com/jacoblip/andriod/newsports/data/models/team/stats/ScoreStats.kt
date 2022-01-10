package com.jacoblip.andriod.newsports.data.models.team.stats

import androidx.annotation.Keep

@Keep
class ScoreStats(
        val total: Int,
        val home: Int,
        val away: Int
)