package com.jacoblip.andriod.newsports.utilities


const val API_KEY = "9vvPM6pxz3sE6bhYSKuoE0d7y6EA3OUNI9VjV0ITgnTm0yXgZyWtzcoRPIUn"
const val englishLang = "en"
const val selectedLang = englishLang

const val BASE_URL = "https://soccer.sportmonks.com/api/v2.0/"
const val YESTERDAY_MATCHES = "yesterday"
const val TODAY_MATCHES = "today"
const val TOMORROW_MATCHES = "tomorrow"

const val RESULTS_MATCHES = "results"
const val CURRENT_MATCHES = "current"
const val UPCOMING_MATCHES = "upcoming"

const val LIVE_MATCHES = "live"
const val FAVORITE_MATCHES = "favorites"

const val queryLimit = 30
private const val playersQueryLimit = 20
const val limit = ":limit($queryLimit|1)"

const val playerLimit = ":limit($playersQueryLimit|1)"

const val chatsBatchCount = 20

const val whatsappContact = "+972506745639"
const val facebookLink = "https://web.facebook.com/ronolikeaaron"
const val twitterLink = "http://twitter.com/Aroniez"

val dirtyWords = arrayOf(
    "bad_words"
)