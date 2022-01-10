package com.jacoblip.andriod.newsports.data.models.country

import androidx.annotation.Keep
import com.jacoblip.andriod.newsports.data.models.competitions.Competition
import java.io.Serializable
@Keep
class CompetitionsData(var data: ArrayList<Competition>) : Serializable