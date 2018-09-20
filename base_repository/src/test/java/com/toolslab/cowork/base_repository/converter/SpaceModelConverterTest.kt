package com.toolslab.cowork.base_repository.converter

import com.toolslab.cowork.base_network.model.Map
import com.toolslab.cowork.base_network.model.Space
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class SpaceModelConverterTest {

    private val space = Space("city", Map("address", "1.1", "2.2"), "space", "slug")
    private val repositorySpace = com.toolslab.cowork.base_repository.model.Space(space.name, space.map.address, 1.1, 2.2)

    private val underTest = SpaceModelConverter()

    @Before
    fun setUp() {
        underTest.coordinateConverter = CoordinateConverter()
    }

    @Test
    fun convert() {
        val result = underTest.convert(space)

        result shouldEqual repositorySpace
    }

}
