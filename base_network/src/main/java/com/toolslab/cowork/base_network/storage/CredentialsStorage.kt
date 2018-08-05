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

    fun saveCredentials(credentials: Credentials) {
        Companion.credentials = credentials
    }

    fun getCredentials() = Companion.credentials

    private companion object {
        // TODO solve without static variable
        @VisibleForTesting
        @JvmStatic
        private lateinit var credentials: Credentials
    }

}
