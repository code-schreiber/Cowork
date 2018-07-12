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
import io.reactivex.Observable
import retrofit2.http.*

// See https://coworkingmap.org/api/docs/
interface CoworkingMapService {

    @POST(JWT_AUTH)
    fun getJwt(
            @Query(USERNAME) user: String,
            @Query(PASSWORD) password: String
    ): Observable<Jwt>

    @POST(VALIDATE)
    fun validate(
            @Header(AUTHORIZATION) token: String
    ): Observable<Validation>

    @GET(SPACES_OF_COUNTRY)
    fun spaces(
            @Header(AUTHORIZATION) token: String,
            @Path(COUNTRY) country: String
    ): Observable<List<Space>>

    @GET(SPACES_OF_CITY)
    fun spaces(
            @Header(AUTHORIZATION) token: String,
            @Path(COUNTRY) country: String,
            @Path(CITY) city: String
    ): Observable<List<Space>>

    @GET(SPACES_OF_SPACE)
    fun spaces(
            @Header(AUTHORIZATION) token: String,
            @Path(COUNTRY) country: String,
            @Path(CITY) city: String,
            @Path(SPACE) space: String
    ): Observable<List<Space>>

}
