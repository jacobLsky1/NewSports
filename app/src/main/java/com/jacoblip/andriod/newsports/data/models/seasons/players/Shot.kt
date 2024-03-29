package com.jacoblip.andriod.newsports.data.models.seasons.players

import androidx.annotation.Keep

@Keep
class Shot(
        val shots_total: Int? = null,
        val shots_on_target: Int? = null,
        val shots_off_target: Int? = null
)