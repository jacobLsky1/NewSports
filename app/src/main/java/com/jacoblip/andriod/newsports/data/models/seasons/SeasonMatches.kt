package com.jacoblip.andriod.newsports.data.models.seasons


import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback
import com.jacoblip.andriod.newsports.data.models.fixture.round.RoundData

@Keep
class SeasonMatches(
    val id: Long,
    val name: Boolean,
    val league_id: Long,
    val is_current_season: Boolean,
    val current_round_id: Long,
    val current_stage_id: Long,
    val results: MatchesCallback,
    val round: RoundData,
    val upcoming: MatchesCallback
)