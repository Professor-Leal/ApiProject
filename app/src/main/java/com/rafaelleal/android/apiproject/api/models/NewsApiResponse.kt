package com.rafaelleal.android.apiproject.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsApiResponse(
    val status: String? ="",
    val totalResults: Int? = 0,
    val articles: List<Article>? = null
)
