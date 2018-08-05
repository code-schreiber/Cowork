package com.toolslab.cowork.base_network

import com.toolslab.cowork.base_network.model.Space
import com.toolslab.cowork.base_network.service.CoworkingMapService
import io.reactivex.Single
import javax.inject.Inject

class CoworkingMapApi @Inject constructor() {

    @Inject
    internal lateinit var coworkingMapService: CoworkingMapService

    fun listSpaces(country: String, city: String, space: String): Single<List<Space>> {
        return if (city.isNotEmpty() && space.isNotEmpty()) {
            coworkingMapService.listSpaces(country, city, space).map { listOf(it) }
        } else if (city.isNotEmpty()) {
            coworkingMapService.listSpaces(country, city)
        } else {
            coworkingMapService.listSpaces(country)
        }
    }

}
