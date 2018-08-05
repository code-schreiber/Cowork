package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_network.CoworkingMapApi
import com.toolslab.cowork.base_network.storage.Credentials
import com.toolslab.cowork.base_network.storage.CredentialsStorage
import com.toolslab.cowork.base_repository.converter.SpaceModelConverter
import com.toolslab.cowork.base_repository.model.Space
import io.reactivex.Single
import javax.inject.Inject

class SpaceRepository @Inject constructor() {

    @Inject
    internal lateinit var credentialsStorage: CredentialsStorage

    @Inject
    internal lateinit var coworkingMapApi: CoworkingMapApi

    @Inject
    internal lateinit var errorHandler: ErrorHandler

    @Inject
    internal lateinit var spaceModelConverter: SpaceModelConverter

    fun listSpaces(credentials: Credentials, country: String, city: String, space: String): Single<MutableList<Space>> {
        credentialsStorage.saveCredentials(credentials)
        return coworkingMapApi.listSpaces(country, city, space)
                .onErrorResumeNext {
                    errorHandler.handle(it)
                }
                .flattenAsObservable { it }
                .map {
                    spaceModelConverter.convert(it)
                }
                .toList()
    }

}
