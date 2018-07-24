package com.toolslab.base_network.model

import com.google.gson.annotations.SerializedName


data class Space(

        @SerializedName("city")
        val city: String,

        @SerializedName("map")
        val map: Map,

        @SerializedName("name")
        val name: String,

        @SerializedName("slug")
        val slug: String
)
