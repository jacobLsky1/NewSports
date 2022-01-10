package com.jacoblip.andriod.newsports.data.models.odds

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.odds.OddValuesData
@Keep
data class Bookmaker(
        var id: Long,
        var name: String,
        var odds: OddValuesData
)