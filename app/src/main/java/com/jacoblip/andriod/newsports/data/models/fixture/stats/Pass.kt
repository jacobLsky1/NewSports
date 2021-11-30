package com.jacoblip.andriod.newsports.data.models.fixture.stats

import java.io.Serializable

data class Pass(
        val total: Int,
        val accurate: Int,
        val percentage: Int
) : Serializable