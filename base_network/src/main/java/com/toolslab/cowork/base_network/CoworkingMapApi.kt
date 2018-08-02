package com.toolslab.cowork.base_network

import android.support.annotation.VisibleForTesting
import com.toolslab.cowork.base_network.model.Space
import io.reactivex.Single
import javax.inject.Inject

class CoworkingMapApi @Inject constructor() {

    @Inject
    internal lateinit var coworkingMapService: CoworkingMapService

    fun getJwt(user: String, password: String) = coworkingMapService.getJwt(user, password)

    fun listSpaces(token: String, country: String, city: String, space: String): Single<List<Space>> {
        val tokenForRequest = createTokenForRequest(token)
        return if (city.isNotEmpty() && space.isNotEmpty()) {
            coworkingMapService.listSpaces(tokenForRequest, country, city, space).map { listOf(it) }
        } else if (city.isNotEmpty()) {
            coworkingMapService.listSpaces(tokenForRequest, country, city)
        } else {
            coworkingMapService.listSpaces(tokenForRequest, country)
        }
    }

    fun validate(token: String) = coworkingMapService.validate(token)

    @VisibleForTesting
    fun createTokenForRequest(token: String) = "Bearer $token"

}
