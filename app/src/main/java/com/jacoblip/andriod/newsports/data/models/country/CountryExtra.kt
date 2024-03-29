package com.jacoblip.andriod.newsports.data.models.country

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class CountryExtra(
        val continent: String,
        val sub_region: String,
        val world_region: String,
        val fifa: String,
        val iso: String,
        val iso2: String,
        val longitude: String,
        val latitude: String,
        val flag: String
) : Serializable
