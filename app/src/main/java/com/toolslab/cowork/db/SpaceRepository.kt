package com.toolslab.cowork.db

import com.toolslab.cowork.network.NetworkHandler
import com.toolslab.cowork.network.model.Space
import io.reactivex.Observable
import javax.inject.Inject

class SpaceRepository @Inject constructor() {

    @Inject
    internal lateinit var networkHandler: NetworkHandler

    fun listSpaces(country: String, city: String, space: String): Observable<List<Space>> {
        return networkHandler.listSpaces(country, city, space).toObservable()
    }

}
