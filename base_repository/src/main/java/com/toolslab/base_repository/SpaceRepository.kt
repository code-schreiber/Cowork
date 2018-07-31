package com.toolslab.base_repository

import com.toolslab.base_network.CoworkingMapApi
import com.toolslab.base_network.model.Space
import io.reactivex.Single
import javax.inject.Inject

class SpaceRepository @Inject constructor() {

    @Inject
    internal lateinit var coworkingMapApi: CoworkingMapApi

    @Inject
    internal lateinit var errorHandler: ErrorHandler

    fun listSpaces(country: String, city: String, space: String): Single<List<Space>> =
    // No db yet, just get data through api call
            coworkingMapApi.listSpaces(city, space, country)
                    .onErrorResumeNext {
                        errorHandler.handle(it)
                    }

}
