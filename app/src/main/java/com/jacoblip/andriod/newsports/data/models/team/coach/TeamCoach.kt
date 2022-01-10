package com.jacoblip.andriod.newsports.data.models.team.coach

import androidx.annotation.Keep
import java.io.Serializable
@Keep
class TeamCoach(
        val coach_id: Long,
        val team_id: Long,
        val country_id: Long,
        val common_name: String,
        val fullname: String,
        val firstname: String,
        val lastname: String,
        val nationality: String,
        val birthdate: String,
        val birthcountry: String,
        val image_path: String
) : Serializable