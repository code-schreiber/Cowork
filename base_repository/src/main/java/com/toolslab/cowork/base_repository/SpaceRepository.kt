package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_network.CoworkingMapApi
import com.toolslab.cowork.base_repository.converter.SpaceModelConverter
import com.toolslab.cowork.base_repository.model.Credentials
import com.toolslab.cowork.base_repository.model.Space
import io.reactivex.Single
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

class SpaceRepository @Inject constructor() {

    @Inject
    internal lateinit var tokenRepository: TokenRepository

    @Inject
    internal lateinit var coworkingMapApi: CoworkingMapApi

    @Inject
    internal lateinit var errorHandler: ErrorHandler

    @Inject
    internal lateinit var spaceModelConverter: SpaceModelConverter

    private lateinit var credentials: Credentials

    fun listSpaces(credentials: Credentials, country: String, city: String, space: String): Single<List<Space>> {
        this.credentials = credentials
        return listSpaces(country, city, space)
                .flattenAsObservable { it }
                .map {
                    spaceModelConverter.convert(it)
                }
                .toList()
    }

    private fun listSpaces(country: String, city: String, space: String)
            : Single<List<com.toolslab.cowork.base_network.model.Space>> {

        val single = if (tokenRepository.isTokenValid()) {
            coworkingMapApi.listSpaces(tokenRepository.getToken(), country, city, space)
        } else {
            listSpacesAuthenticating(country, city, space)
        }
        return single.onErrorResumeNext {
            if (errorHandler.isTokenExpired(it)) {
                Logger.getGlobal().log(Level.FINE, "Token has expired") // TODO does this show up in logcat?
                tokenRepository.invalidateToken()
                listSpacesAuthenticating(country, city, space)
            } else {
                errorHandler.handle(it)
            }
        }
    }

    private fun listSpacesAuthenticating(country: String, city: String, space: String) =
            coworkingMapApi.getJwt(credentials.user, credentials.password)
                    .flatMap {
                        tokenRepository.saveToken(it.token)
                        coworkingMapApi.listSpaces(tokenRepository.getToken(), country, city, space)
                    }

}
