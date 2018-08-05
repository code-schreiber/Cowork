package com.toolslab.cowork.base_network

import android.support.annotation.VisibleForTesting
import com.toolslab.cowork.base_network.ApiEndpoint.Header.AUTHORIZATION
import com.toolslab.cowork.base_network.service.CoworkingMapAuthService
import com.toolslab.cowork.base_network.storage.CredentialsStorage
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import javax.inject.Inject

class ReauthenticateInterceptor @Inject constructor() : Interceptor {

    @Inject
    internal lateinit var credentialsStorage: CredentialsStorage

    @Inject
    internal lateinit var coworkingMapAuthService: CoworkingMapAuthService

    override fun intercept(chain: Interceptor.Chain?): Response? {
        if (chain == null) return null
        val response = chain.proceed(chain.request())
        if (response.code() != HTTP_NOT_FOUND) return response

        // The server answers HTTP_NOT_FOUND instead of HTTP_UNAUTHORIZED
        // to requests with an either expired or invalid token.
        // TODO try with real not found responses
        val credentials = credentialsStorage.getCredentials()
        coworkingMapAuthService.getJwt(credentials.user, credentials.password)
                .map { credentialsStorage.saveToken(it.token) }
                .blockingGet()

        val newRequest = response.request().newBuilder()
                .header(AUTHORIZATION, createTokenForRequest(credentialsStorage.getToken()))
                .build()
        return chain.proceed(newRequest)
    }

    @VisibleForTesting
    fun createTokenForRequest(token: String) = "Bearer $token"

}

