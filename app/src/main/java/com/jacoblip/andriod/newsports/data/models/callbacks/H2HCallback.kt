package com.jacoblip.andriod.newsports.data.models.callbacks

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture

@Keep
class H2HCallback(var firstTeam_VS_secondTeam: ArrayList<Fixture>, var firstTeam_lastResults: ArrayList<Fixture>, var secondTeam_lastResults: ArrayList<Fixture>)