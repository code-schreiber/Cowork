package com.toolslab.base_repository

import com.toolslab.base_network.CoworkingMapApi
import com.toolslab.base_network.model.Space
import io.reactivex.Observable
import javax.inject.Inject

class SpaceRepository @Inject constructor() {

    @Inject
    internal lateinit var coworkingMapApi: CoworkingMapApi

    @Inject
    internal lateinit var errorHandler: ErrorHandler

    fun listSpaces(country: String, city: String, space: String): Observable<List<Space>> {
        // No db yet, just get data through api call
        return coworkingMapApi.listSpaces(city, space, country)
                .onErrorResumeNext {
                    errorHandler.handle(it)
                }
                .toObservable()
    }

}
