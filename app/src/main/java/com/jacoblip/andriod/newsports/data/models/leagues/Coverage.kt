package com.jacoblip.andriod.newsports.data.models.leagues

import java.io.Serializable


class Coverage(
        val predictions: Boolean,
        val topscorer_goals: Boolean,
        val topscorer_assists: Boolean,
        val topscorer_cards: Boolean
): Serializable