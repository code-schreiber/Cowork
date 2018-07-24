package com.toolslab.base_network.model

import com.google.gson.annotations.SerializedName

data class Validation(

        @SerializedName("code")
        val code: String,

        @SerializedName("data")
        val data: Data
)
