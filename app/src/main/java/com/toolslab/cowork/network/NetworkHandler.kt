package com.toolslab.cowork.network

import com.toolslab.cowork.network.model.Space
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class NetworkHandler @Inject constructor() {

    @Inject
    internal lateinit var coworkingMapService: CoworkingMapService

    @Inject
    internal lateinit var credentialsProvider: CredentialsProvider

    fun listSpaces(country: String, city: String, space: String): Observable<List<Space>> {
        if (credentialsProvider.token.isEmpty()) {
            Timber.d("Getting jwt first...")
            return coworkingMapService.getJwt(credentialsProvider.user, credentialsProvider.password)
                    .concatMap {
                        Timber.d("Got jwt $it")
                        credentialsProvider.token = it.token
                        listSpaces(it.token, country, space, city) // TODO handle token not being valid (anymore) or empty or network error
                    }
        } else {
            return listSpaces(credentialsProvider.token, country, space, city)
        }
    }

    private fun listSpaces(token: String, country: String, space: String, city: String): Observable<List<Space>> {
        // TODO servers sends one object instead of list of one when there is only one result
        val tokenForRequest = "Bearer $token"
        return if (city.isNotEmpty() && space.isNotEmpty()) {
            coworkingMapService.listSpaces(tokenForRequest, country, city, space) // TODO handle not found i.e. {"code":"no_spaces","message":"Space not found","data":{"status":404}} for https://coworkingmap.org/wp-json/spaces/Germany/berlin/beta
        } else if (city.isNotEmpty()) {
            coworkingMapService.listSpaces(tokenForRequest, country, city)
        } else {
            coworkingMapService.listSpaces(tokenForRequest, country)
        }
    }

}
