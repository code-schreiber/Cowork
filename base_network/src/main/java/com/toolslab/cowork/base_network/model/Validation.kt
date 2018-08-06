package com.toolslab.cowork.base_network.model

import com.squareup.moshi.Json

data class Validation(

        @Json(name = "code")
        val code: String,

        @Json(name = "data")
        val data: Data
)
