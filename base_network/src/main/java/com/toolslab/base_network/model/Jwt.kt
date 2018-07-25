package com.toolslab.base_network.model

import com.google.gson.annotations.SerializedName

data class Jwt(

        @SerializedName("token")
        val token: String,

        @SerializedName("user_email")
        val userEmail: String,

        @SerializedName("user_nicename")
        val userNiceName: String,

        @SerializedName("user_display_name")
        val userDisplayName: String,

        @SerializedName("request")
        val request: Int
)
