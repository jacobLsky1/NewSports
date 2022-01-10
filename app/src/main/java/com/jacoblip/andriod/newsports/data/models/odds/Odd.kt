package com.jacoblip.andriod.newsports.data.models.odds

import androidx.annotation.Keep

@Keep
data class Odd(
        var id: Long,
        var name: String,
        var suspended: Boolean,
        var bookmaker: BookmakerData
)