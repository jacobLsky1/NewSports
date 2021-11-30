package com.jacoblip.andriod.newsports.data.models.odds

import com.jacoblip.andriod.newsports.data.models.odds.OddValuesData

data class Bookmaker(
        var id: Long,
        var name: String,
        var odds: OddValuesData
)