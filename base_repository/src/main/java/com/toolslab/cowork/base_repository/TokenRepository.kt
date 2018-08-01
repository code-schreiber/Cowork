package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_repository.model.Token
import javax.inject.Inject

class TokenRepository @Inject constructor() {

    internal val cachedToken = Token()

    fun getToken() = cachedToken.token

    fun saveToken(token: String) {
        cachedToken.token = token // TODO persist token in database
    }

    fun invalidateToken() {
        cachedToken.invalidate()
    }

    fun isTokenValid() = cachedToken.isValid()
}
