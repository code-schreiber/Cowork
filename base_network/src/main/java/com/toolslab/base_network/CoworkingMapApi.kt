package com.toolslab.base_network

import android.support.annotation.VisibleForTesting
import com.toolslab.base_network.model.Credentials
import com.toolslab.base_network.model.Space
import io.reactivex.Single
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

class CoworkingMapApi @Inject constructor() {

    @Inject
    internal lateinit var coworkingMapService: CoworkingMapService

    internal val credentials = Credentials()

    fun listSpaces(city: String, space: String, country: String): Single<List<Space>> {
        return if (this.credentials.token.isEmpty()) {
            listSpacesAuthenticatingFirst(country, space, city)
        } else {
            listSpacesAlreadyAuthenticated(country, space, city)
        }
    }

    private fun listSpacesAuthenticatingFirst(country: String, space: String, city: String): Single<List<Space>> {
        return coworkingMapService.getJwt(credentials.user, credentials.password)
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
        return if (isTokenExpired(throwable)) {
            Logger.getGlobal().log(Level.FINE, "Toke was expired") // TODO does this show up in logcat?
            credentials.token = ""
            listSpaces(city, space, country)
        } else {
            Single.error(throwable)
        }
    }

    @VisibleForTesting // TODO test
    fun isTokenExpired(throwable: Throwable) = throwable is HttpException && throwable.code() == HttpURLConnection.HTTP_FORBIDDEN

    @VisibleForTesting
    fun createTokenForRequest(token: String) = "Bearer $token"

}
