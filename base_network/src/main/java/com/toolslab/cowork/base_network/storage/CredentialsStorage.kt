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

    fun getCredentials() = Companion.credentials

    fun saveCredentials(credentials: Credentials) {
        Companion.credentials = credentials
    }

    @VisibleForTesting
    internal companion object {
        // TODO solve without static variable
        @VisibleForTesting
        @JvmStatic
        internal lateinit var credentials: Credentials
    }

}
