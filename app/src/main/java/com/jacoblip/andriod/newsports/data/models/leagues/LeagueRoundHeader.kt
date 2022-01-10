package com.jacoblip.andriod.newsports.data.models.leagues

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.bignerdranch.expandablerecyclerview.model.Parent
import java.io.Serializable
@Keep
class LeagueRoundHeader(
        var round: String? = "",
        var fixtures: List<Fixture>? = null
) : Serializable, Parent<Fixture> {
    override fun getChildList() = fixtures

    override fun isInitiallyExpanded() = true
}