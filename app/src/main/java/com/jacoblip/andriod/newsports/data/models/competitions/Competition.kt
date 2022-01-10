package com.jacoblip.andriod.newsports.data.models.competitions

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jacoblip.andriod.newsports.data.models.country.CountryData
import com.jacoblip.andriod.newsports.data.models.fixture.round.Round
import com.jacoblip.andriod.newsports.data.models.leagues.Coverage
import com.jacoblip.andriod.newsports.data.models.seasons.SeasonData
import java.io.Serializable
@Keep
@Entity(tableName = "competitions")
class Competition(
    @PrimaryKey val id: Long,
    val active: Boolean,
    val is_cup: Boolean,
    val live_standings: Boolean,
    val type: String,
    val legacy_id: Int,
    val country_id: Int,
    val current_season_id: Long,
    val current_round_id: Long,
    val current_stage_id: Long,
    val logo_path: String? = null,
    val name: String,
    val coverage: Coverage,
    var round: Round?,
    var season: SeasonData?,
    val country: CountryData
): Serializable