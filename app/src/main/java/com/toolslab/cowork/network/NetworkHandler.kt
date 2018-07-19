package com.toolslab.cowork.network

import android.support.annotation.VisibleForTesting
import com.toolslab.cowork.network.model.Space
import io.reactivex.Single
import io.reactivex.SingleSource
import timber.log.Timber
import javax.inject.Inject

class NetworkHandler @Inject constructor() {

    @Inject
    internal lateinit var coworkingMapService: CoworkingMapService

    @Inject
    internal lateinit var credentialsProvider: CredentialsProvider

    @Inject
    internal lateinit var httpErrorHandler: HttpErrorHandler

    fun listSpaces(country: String, city: String, space: String): Single<List<Space>> {
        return if (credentialsProvider.token.isEmpty()) {
            listSpacesAuthenticatingFirst(country, space, city)
        } else {
            listSpaces(credentialsProvider.token, country, space, city)
        }
    }

    private fun listSpacesAuthenticatingFirst(country: String, space: String, city: String): Single<List<Space>> {
        Timber.d("Getting jwt first...")
        return coworkingMapService.getJwt(credentialsProvider.user, credentialsProvider.password)
                .onErrorResumeNext { throwable: Throwable ->
                    httpErrorHandler.handle(throwable)
                }
                .flatMap {
                    Timber.d("Got a new jwt")
                    credentialsProvider.token = it.token
                    listSpaces(it.token, country, space, city)
                }
    }

    private fun listSpaces(token: String, country: String, space: String, city: String): Single<List<Space>> {
        val tokenForRequest = createTokenForRequest(token)
        val single = if (city.isNotEmpty() && space.isNotEmpty()) {
            coworkingMapService.listSpaces(tokenForRequest, country, city, space).map { listOf(it) }
        } else if (city.isNotEmpty()) {
            coworkingMapService.listSpaces(tokenForRequest, country, city)
        } else {
            coworkingMapService.listSpaces(tokenForRequest, country)
        }
        return single.onErrorResumeNext { throwable: Throwable ->
            handleExpiredToken(throwable, country, city, space)
        }
    }

    private fun handleExpiredToken(throwable: Throwable, country: String, city: String, space: String): SingleSource<out List<Space>> {
        return if (httpErrorHandler.isTokenExpired(throwable)) {
            Timber.d("Jwt has expired")
            credentialsProvider.token = ""
            listSpaces(country, city, space)
        } else {
            httpErrorHandler.handle(throwable)
        }
    }

    @VisibleForTesting
    fun createTokenForRequest(token: String) = "Bearer $token"

}
