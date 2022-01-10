package com.jacoblip.andriod.newsports.data.models

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class Competition(
        var name: String? = "",
        var id: Int? = 0,
        var url: String? = ""
):Serializable