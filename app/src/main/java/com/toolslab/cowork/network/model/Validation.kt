package com.toolslab.cowork.network.model

import com.google.gson.annotations.SerializedName

data class Validation(

        @SerializedName("code")
        val code: String,

        @SerializedName("data")
        val data: Data
)
