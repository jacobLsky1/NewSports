package com.jacoblip.andriod.newsports.data.models

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class Side(
        var name: String? = "",
        var url: String? = ""
):Serializable