package com.jacoblip.andriod.newsports.data.models.country

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.competitions.Competition
import com.bignerdranch.expandablerecyclerview.model.Parent
import java.io.Serializable

@Keep
class Country(
    val id: Int,
    val name: String,
    val image_path: String,
    var leagues: CompetitionsData,
    val extra: CountryExtra
) : Serializable, Parent<Competition> {
    override fun getChildList() = leagues.data

    override fun isInitiallyExpanded() = true
}
