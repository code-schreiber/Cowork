package com.toolslab.cowork.network

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
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// See https://coworkingmap.org/api/docs/
interface CoworkingMapService {

    @POST(JWT_AUTH)
    fun getJwt(
            @Query(USERNAME) user: String,
            @Query(PASSWORD) password: String
    ): Observable<Jwt>

    @POST(VALIDATE)
    fun validate(): Observable<String>

    @GET(SPACES_OF_COUNTRY)
    fun spaces(
            @Path(COUNTRY) country: String
    ): Observable<Space>

    @GET(SPACES_OF_CITY)
    fun spaces(
            @Path(COUNTRY) country: String,
            @Path(CITY) city: String
    ): Observable<Space>

    @GET(SPACES_OF_SPACE)
    fun spaces(
            @Path(COUNTRY) country: String,
            @Path(CITY) city: String,
            @Path(SPACE) space: String
    ): Observable<Space>

}
