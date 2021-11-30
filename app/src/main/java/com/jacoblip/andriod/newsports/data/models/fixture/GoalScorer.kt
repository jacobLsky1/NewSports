package com.jacoblip.andriod.newsports.data.models.fixture

import java.io.Serializable

data class GoalScorer(
    val time: String,
    val home_scorer: String,
    val score: String,
    val away_scorer: String
): Serializable