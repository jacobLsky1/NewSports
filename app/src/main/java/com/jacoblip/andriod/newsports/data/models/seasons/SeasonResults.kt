package com.jacoblip.andriod.newsports.data.models.seasons

import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback


class SeasonResults(
        val id: Long,
        val name: Boolean,
        val league_id: Long,
        val is_current_season: Boolean,
        val current_round_id: Long,
        val current_stage_id: Long,
        val results: MatchesCallback
)