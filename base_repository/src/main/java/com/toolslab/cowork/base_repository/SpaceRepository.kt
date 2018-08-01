package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_network.CoworkingMapApi
import com.toolslab.cowork.base_network.model.Space
import com.toolslab.cowork.base_repository.model.Credentials
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

    private lateinit var credentials: Credentials

    fun listSpaces(credentials: Credentials, country: String, city: String, space: String): Single<List<Space>> {
        this.credentials = credentials
        return listSpaces(country, space, city)
    }

    private fun listSpaces(country: String, space: String, city: String): Single<List<Space>> {
        val single = if (tokenRepository.getToken().isValid()) {
            listSpacesAlreadyAuthenticated(country, space, city)
        } else {
            listSpacesAuthenticatingFirst(country, space, city)
        }
        return single.onErrorResumeNext {
            errorHandler.handle(it)
        }
    }

    private fun listSpacesAuthenticatingFirst(country: String, space: String, city: String) =
            coworkingMapApi.getJwt(credentials.user, credentials.password)
                    .flatMap {
                        tokenRepository.saveToken(it.token)
                        listSpacesAlreadyAuthenticated(country, space, city)
                    }

    private fun listSpacesAlreadyAuthenticated(country: String, space: String, city: String) =
            coworkingMapApi.listSpacesAlreadyAuthenticated(tokenRepository.getToken().token, country, space, city)
                    .onErrorResumeNext {
                        handleExpiredToken(it, country, city, space)
                    }

    private fun handleExpiredToken(throwable: Throwable, country: String, city: String, space: String): Single<List<Space>> =
            if (coworkingMapApi.isTokenExpired(throwable)) {
                // TODO for debuging expired token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvY293b3JraW5nbWFwLm9yZyIsImlhdCI6MTUzMTQwMTk3NCwibmJmIjoxNTMxNDAxOTc0LCJleHAiOjE1MzIwMDY3NzQsImRhdGEiOnsidXNlciI6eyJpZCI6IjI2NDYifX19.uVwF7RdTmeB8ANmt0rTCTzXMCN7zEq5znYC-wuorRQE"
                Logger.getGlobal().log(Level.FINE, "Token was expired") // TODO does this show up in logcat?
                tokenRepository.invalidateToken()
                listSpaces(city, space, country)
            } else {
                Single.error(throwable)
            }

}
