package com.toolslab.base_network

import android.support.annotation.VisibleForTesting
import com.toolslab.base_network.model.Credentials
import com.toolslab.base_network.model.Space
import io.reactivex.Single
import javax.inject.Inject

class CoworkingMapApi @Inject constructor() {

    @Inject
    internal lateinit var coworkingMapService: CoworkingMapService

    @Inject
    internal lateinit var httpErrorHandler: HttpErrorHandler

    private val credentials = Credentials()

    fun listSpaces(city: String, space: String, country: String): Single<List<Space>> {
        return if (this.credentials.token.isEmpty()) {
            listSpacesAuthenticatingFirst(country, space, city)
        } else {
            listSpacesAlreadyAuthenticated(country, space, city)
        }
    }

    private fun listSpacesAuthenticatingFirst(country: String, space: String, city: String): Single<List<Space>> {
        return coworkingMapService.getJwt(credentials.user, credentials.password)
                .onErrorResumeNext { throwable: Throwable ->
                    httpErrorHandler.handle(throwable)
                }
                .flatMap {
                    credentials.token = it.token // TODO persist token in database
                    listSpacesAlreadyAuthenticated(country, space, city)
                }
    }

    private fun listSpacesAlreadyAuthenticated(country: String, space: String, city: String): Single<List<Space>> {
        val tokenForRequest = createTokenForRequest(credentials.token)
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

    private fun handleExpiredToken(throwable: Throwable, country: String, city: String, space: String): Single<List<Space>> {
        return if (httpErrorHandler.isTokenExpired(throwable)) {
            credentials.token = ""
            listSpaces(city, space, country)
        } else {
            httpErrorHandler.handle(throwable)
        }
    }

    @VisibleForTesting
    fun createTokenForRequest(token: String) = "Bearer $token"

}
