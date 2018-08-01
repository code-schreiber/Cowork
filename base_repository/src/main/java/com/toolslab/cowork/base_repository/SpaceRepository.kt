package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_repository.model.Credentials
import com.toolslab.cowork.base_repository.model.Space
import io.reactivex.Observable
import javax.inject.Inject

class SpaceRepository @Inject constructor() {

    @Inject
    internal lateinit var tokenRepository: TokenRepository

    @Inject
    internal lateinit var errorHandler: ErrorHandler

    private lateinit var credentials: Credentials

    fun listSpaces(credentials: Credentials, country: String, city: String, space: String): Observable<List<Space>> {
        this.credentials = credentials
        return Observable.empty()
    }

}
