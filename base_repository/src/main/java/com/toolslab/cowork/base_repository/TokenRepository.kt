package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_repository.model.Token
import javax.inject.Inject

class TokenRepository @Inject constructor() {

    internal val cachedToken = Token(
            //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvY293b3JraW5nbWFwLm9yZyIsImlhdCI6MTUzMTQwMTk3NCwibmJmIjoxNTMxNDAxOTc0LCJleHAiOjE1MzIwMDY3NzQsImRhdGEiOnsidXNlciI6eyJpZCI6IjI2NDYifX19.uVwF7RdTmeB8ANmt0rTCTzXMCN7zEq5znYC-wuorRQE"
    )

    fun getToken() = cachedToken

    fun saveToken(token: String) {
        cachedToken.token = token // TODO persist token in database
    }

    fun invalidateToken() {
        cachedToken.invalidate()
    }

}
