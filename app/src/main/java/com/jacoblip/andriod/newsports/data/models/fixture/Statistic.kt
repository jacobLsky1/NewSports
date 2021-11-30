package com.jacoblip.andriod.newsports.data.models.fixture

import java.io.Serializable

data class Statistic(
    val type: String,
    val home: String,
    val away: String
): Serializable