package com.toolslab.cowork.app.view.map

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.cowork.base_network.storage.Credentials
import com.toolslab.cowork.base_repository.model.Space
import io.reactivex.Single
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class CoworkInteractorTest {

    private val country = "a country"
    private val city = "a city"
    private val space = "a space"
    private val credentials = Credentials("user", "pw")

    private val underTest = CoworkInteractor()

    @Before
    fun setUp() {
        underTest.spaceRepository = mock()
    }

    @Test
    fun listSpaces() {
        val expected = listOf(Space("space1", "snippet1", 1.1, 1.1))
        whenever(underTest.spaceRepository.listSpaces(credentials, country, city, space)).thenReturn(Single.just(expected))

        val spaces = underTest.listSpaces(credentials, country, city, space).blockingGet()

        spaces shouldEqual expected
    }
}
