package com.jacoblip.andriod.newsports.data.models.odds

data class Odd(
        var id: Long,
        var name: String,
        var suspended: Boolean,
        var bookmaker: BookmakerData
)