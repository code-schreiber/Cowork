package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_repository.model.Token
import javax.inject.Inject

class TokenRepository @Inject constructor() {

    internal val cachedToken = Token()

    internal fun getToken() = cachedToken.token

    internal fun saveToken(token: String) {
        cachedToken.token = token // TODO persist token in database
    }

    internal fun invalidateToken() {
        cachedToken.invalidate()
    }

    internal fun isTokenValid() = cachedToken.isValid()
}
