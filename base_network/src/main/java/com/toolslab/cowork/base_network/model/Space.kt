package com.toolslab.cowork.base_network.model

import com.squareup.moshi.Json

data class Space(

        @Json(name = "city")
        val city: String,

        @Json(name = "map")
        val map: Map,

        @Json(name = "name")
        val name: String,

        @Json(name = "slug")
        val slug: String
)
