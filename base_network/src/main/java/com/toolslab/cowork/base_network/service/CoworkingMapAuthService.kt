package com.toolslab.cowork.base_network.service

import com.toolslab.cowork.base_network.ApiEndpoint.Endpoint.JWT_AUTH
import com.toolslab.cowork.base_network.ApiEndpoint.Endpoint.VALIDATE
import com.toolslab.cowork.base_network.ApiEndpoint.Header.AUTHORIZATION
import com.toolslab.cowork.base_network.ApiEndpoint.Query.PASSWORD
import com.toolslab.cowork.base_network.ApiEndpoint.Query.USERNAME
import com.toolslab.cowork.base_network.model.Jwt
import com.toolslab.cowork.base_network.model.Validation
import io.reactivex.Single
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CoworkingMapAuthService {

    @POST(JWT_AUTH)
    fun getJwt(
            @Query(USERNAME) user: String,
            @Query(PASSWORD) password: String
    ): Single<Jwt>

    @POST(VALIDATE)
    fun validate(
            @Header(AUTHORIZATION) token: String
    ): Single<Validation>

}
