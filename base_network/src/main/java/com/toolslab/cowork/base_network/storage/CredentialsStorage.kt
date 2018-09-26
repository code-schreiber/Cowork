package com.toolslab.cowork.base_network.storage

import android.support.annotation.VisibleForTesting
import javax.inject.Inject

class CredentialsStorage @Inject constructor() {

    @VisibleForTesting
    internal val cachedToken = Token("")

    internal fun getToken() = cachedToken.token

    internal fun saveToken(token: String) {
        cachedToken.token = token // TODO persist token in database
    }

    internal fun getCredentials() = credentials

    fun saveCredentials(creds: Credentials) {
        credentials = creds
    }

    private companion object {
        // TODO solve without static variable
        @JvmStatic
        private lateinit var credentials: Credentials
    }

}
