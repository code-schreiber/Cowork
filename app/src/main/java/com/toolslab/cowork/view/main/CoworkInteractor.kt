package com.toolslab.cowork.view.main

import android.support.annotation.CheckResult
import com.toolslab.cowork.base_network.storage.Credentials
import com.toolslab.cowork.base_repository.SpaceRepository
import javax.inject.Inject

class CoworkInteractor @Inject constructor() {

    @Inject
    internal lateinit var spaceRepository: SpaceRepository

    @CheckResult
    internal fun listSpaces(credentials: Credentials, country: String, city: String, space: String) =
            spaceRepository.listSpaces(credentials, country, city, space)

}
