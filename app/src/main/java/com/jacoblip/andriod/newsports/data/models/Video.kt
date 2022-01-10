package com.jacoblip.andriod.newsports.data.models

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class Video(
        var title: String,
        var embed: String? = ""
):Serializable