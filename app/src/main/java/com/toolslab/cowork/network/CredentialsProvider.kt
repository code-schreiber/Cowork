package com.toolslab.cowork.network

import com.toolslab.cowork.BuildConfig
import javax.inject.Inject

class CredentialsProvider @Inject constructor() {

    var token = ""

    val user = BuildConfig.API_USER
    val password = BuildConfig.API_PASSWORD

}
