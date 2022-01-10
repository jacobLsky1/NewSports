package com.jacoblip.andriod.newsports.data.models.fixture

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class Substitution(
    val time: String,
    val substitution: String
): Serializable