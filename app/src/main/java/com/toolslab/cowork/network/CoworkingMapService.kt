package com.toolslab.cowork.network

import com.toolslab.cowork.network.ApiEndpoint.Header.AUTHORIZATION
import com.toolslab.cowork.network.ApiEndpoint.JWT_AUTH
import com.toolslab.cowork.network.ApiEndpoint.Path.CITY
import com.toolslab.cowork.network.ApiEndpoint.Path.COUNTRY
import com.toolslab.cowork.network.ApiEndpoint.Path.SPACE
import com.toolslab.cowork.network.ApiEndpoint.Query.PASSWORD
import com.toolslab.cowork.network.ApiEndpoint.Query.USERNAME
import com.toolslab.cowork.network.ApiEndpoint.SPACES_OF_CITY
import com.toolslab.cowork.network.ApiEndpoint.SPACES_OF_COUNTRY
import com.toolslab.cowork.network.ApiEndpoint.SPACES_OF_SPACE
import com.toolslab.cowork.network.ApiEndpoint.VALIDATE
import com.toolslab.cowork.network.model.Jwt
import com.toolslab.cowork.network.model.Space
import com.toolslab.cowork.network.model.Validation
import io.reactivex.Single
import retrofit2.http.*

interface CoworkingMapService {

    @POST(JWT_AUTH)
    fun getJwt(
            @Query(USERNAME) user: String,
            @Query(PASSWORD) password: String
    ): Single<Jwt>

    @POST(VALIDATE)
    fun validate(
            @Header(AUTHORIZATION) token: String
    ): Single<Validation>

    @GET(SPACES_OF_COUNTRY)
    fun listSpaces(
            @Header(AUTHORIZATION) token: String,
            @Path(COUNTRY) country: String
    ): Single<List<Space>>

    @GET(SPACES_OF_CITY)
    fun listSpaces(
            @Header(AUTHORIZATION) token: String,
            @Path(COUNTRY) country: String,
            @Path(CITY) city: String
    ): Single<List<Space>>

    @GET(SPACES_OF_SPACE)
    fun listSpaces(
            @Header(AUTHORIZATION) token: String,
            @Path(COUNTRY) country: String,
            @Path(CITY) city: String,
            @Path(SPACE) space: String
    ): Single<List<Space>>

}
