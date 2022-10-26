package com.rafaelleal.android.apiproject.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(
    val id: String? = null,
    val name: String? = null

)
