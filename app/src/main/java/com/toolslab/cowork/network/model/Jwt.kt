package com.toolslab.cowork.network.model

data class Jwt(
        val token: String,
        val userEmail: String,
        val userNiceName: String,
        val userDisplayName: String,
        val request: Int
)
