package com.toolslab.cowork.base_network.model

import com.squareup.moshi.Json

data class Jwt(

        @Json(name = "token")
        val token: String,

        @Json(name = "user_email")
        val userEmail: String,

        @Json(name = "user_nicename")
        val userNiceName: String,

        @Json(name = "user_display_name")
        val userDisplayName: String,

        @Json(name = "request")
        val request: Int
)
