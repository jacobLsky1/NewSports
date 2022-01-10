package com.jacoblip.andriod.newsports.data.models.fixture.venue

import androidx.annotation.Keep
import java.io.Serializable
@Keep

class Venue(
        val id: Long,
        val name: String,
        val surface: String,
        val address: String,
        val city: String,
        val capacity: Long,
        val image_path: String,
        val coordinates: String
): Serializable