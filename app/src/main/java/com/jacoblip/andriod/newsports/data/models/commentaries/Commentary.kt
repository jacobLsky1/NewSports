package com.jacoblip.andriod.newsports.data.models.commentaries


data class Commentary(
        val fixture_id: Long,
        val important: Boolean,
        val order: Int,
        val goal: Boolean,
        val minute: Int,
        val extra_minute: Int? = null,
        val comment: String
)