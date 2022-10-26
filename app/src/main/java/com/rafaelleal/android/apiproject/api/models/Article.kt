package com.rafaelleal.android.apiproject.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
    val source: Source? = null
)
