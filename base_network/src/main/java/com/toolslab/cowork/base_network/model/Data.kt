package com.toolslab.cowork.base_network.model

import com.squareup.moshi.Json

data class Data(

        @Json(name = "status")
        val status: Int
)
