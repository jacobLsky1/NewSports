package com.jacoblip.andriod.newsports.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.annotation.NonNull
@Keep
@Entity(primaryKeys = ["id"])
data class Favorite(
        @NonNull var id: Long
)