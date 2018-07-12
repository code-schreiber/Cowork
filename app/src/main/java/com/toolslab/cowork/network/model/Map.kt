package com.toolslab.cowork.network.model

import com.google.gson.annotations.SerializedName

data class Map(

        @SerializedName("address")
        val address: String,

        @SerializedName("lat")
        val lat: String,

        @SerializedName("lng")
        val lng: String
)
