package com.toolslab.cowork.base_repository.converter

import com.toolslab.cowork.base_network.model.Map
import com.toolslab.cowork.base_network.model.Space
import org.amshove.kluent.shouldEqual
import org.junit.Test

class SpaceModelConverterTest {

    private val space = Space("city", Map("address", "lat", "lng"), "space", "slug")
    private val repositorySpace = com.toolslab.cowork.base_repository.model.Space(space.name)

    private val underTest = SpaceModelConverter()

    @Test
    fun convert() {
        val result = underTest.convert(space)

        result shouldEqual repositorySpace
    }

}
