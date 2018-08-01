package com.toolslab.cowork.base_network.model

import com.squareup.moshi.Json

data class Map(

        @Json(name = "address")
        val address: String,

        @Json(name = "lat")
        val lat: String,

        @Json(name = "lng")
        val lng: String
)
