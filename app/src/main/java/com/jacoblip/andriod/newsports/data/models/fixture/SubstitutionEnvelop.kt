package com.jacoblip.andriod.newsports.data.models.fixture

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class SubstitutionEnvelop(
    val home: List<Substitution>,
    val away: List<Substitution>
): Serializable