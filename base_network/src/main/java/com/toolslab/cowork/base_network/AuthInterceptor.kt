package com.toolslab.cowork.base_network

import android.support.annotation.VisibleForTesting
import com.toolslab.cowork.base_network.ApiEndpoint.Header.AUTHORIZATION
import com.toolslab.cowork.base_network.service.CoworkingMapAuthService
import com.toolslab.cowork.base_network.storage.CredentialsStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    internal lateinit var credentialsStorage: CredentialsStorage

    @Inject
    internal lateinit var coworkingMapAuthService: CoworkingMapAuthService

    override fun intercept(chain: Interceptor.Chain?): Response? {
        if (chain == null) return null
        val request = addAuthHeader(chain.request())
        val response = chain.proceed(request)

        // The server answers HTTP_NOT_FOUND instead of HTTP_UNAUTHORIZED
        // to requests with an either expired or invalid token.
        return if (response.code() == HTTP_NOT_FOUND) chain.proceed(addAuthHeader(response.request()))
        else response
    }

    @VisibleForTesting
    internal fun addAuthHeader(originalRequest: Request): Request {
        if (originalRequest.header(AUTHORIZATION) != null) return originalRequest

        // No auth token in request, put it in
        if (credentialsStorage.getToken().isEmpty()) {
            // Get and save token first
            val credentials = credentialsStorage.getCredentials()
            coworkingMapAuthService.getJwt(credentials.user, credentials.password)
                    .map { credentialsStorage.saveToken(it.token) }
                    .blockingGet()
        }
        val tokenForRequest = createTokenForRequest(credentialsStorage.getToken())
        return originalRequest.newBuilder()
                .header(AUTHORIZATION, tokenForRequest)
                .build()
    }

    @VisibleForTesting
    internal fun createTokenForRequest(token: String) = "Bearer $token"

}

