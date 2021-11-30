package com.jacoblip.andriod.newsports.data.models.fixture

import java.io.Serializable

data class SubstitutionEnvelop(
    val home: List<Substitution>,
    val away: List<Substitution>
): Serializable