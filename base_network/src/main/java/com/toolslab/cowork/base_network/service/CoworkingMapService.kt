package com.toolslab.cowork.base_network.service

import com.toolslab.cowork.base_network.ApiEndpoint.Endpoint.SPACES_OF_CITY
import com.toolslab.cowork.base_network.ApiEndpoint.Endpoint.SPACES_OF_COUNTRY
import com.toolslab.cowork.base_network.ApiEndpoint.Endpoint.SPACES_OF_SPACE
import com.toolslab.cowork.base_network.ApiEndpoint.Path.CITY
import com.toolslab.cowork.base_network.ApiEndpoint.Path.COUNTRY
import com.toolslab.cowork.base_network.ApiEndpoint.Path.SPACE
import com.toolslab.cowork.base_network.model.Space
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CoworkingMapService {

    @GET(SPACES_OF_COUNTRY)
    fun listSpaces(
            @Path(COUNTRY) country: String
    ): Single<List<Space>>

    @GET(SPACES_OF_CITY)
    fun listSpaces(
            @Path(COUNTRY) country: String,
            @Path(CITY) city: String
    ): Single<List<Space>>

    @GET(SPACES_OF_SPACE)
    fun listSpaces(
            @Path(COUNTRY) country: String,
            @Path(CITY) city: String,
            @Path(SPACE) space: String
    ): Single<Space>

}
