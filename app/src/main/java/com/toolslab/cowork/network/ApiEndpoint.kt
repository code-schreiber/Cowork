package com.toolslab.cowork.network

import com.toolslab.cowork.network.ApiEndpoint.Path.CITY
import com.toolslab.cowork.network.ApiEndpoint.Path.COUNTRY
import com.toolslab.cowork.network.ApiEndpoint.Path.SPACE


internal object ApiEndpoint {

    object Query {
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    object Path {
        const val COUNTRY = "country"
        const val CITY = "city"
        const val SPACE = "space"
    }

    const val JWT_AUTH = "jwt-auth/v1/token/"
    const val VALIDATE = "jwt-auth/v1/token/validate/"

    const val SPACES_OF_COUNTRY = "spaces/{$COUNTRY}"
    const val SPACES_OF_CITY = "spaces/{$COUNTRY}/{$CITY}"
    const val SPACES_OF_SPACE = "spaces/{$COUNTRY}/{$CITY}/{$SPACE}"

    const val API_BASE_URL = "https://coworkingmap.org/wp-json/"

}
