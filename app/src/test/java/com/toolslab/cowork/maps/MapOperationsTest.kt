package com.toolslab.cowork.maps

import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.verify

class MapOperationsTest {

    private val title = "title"
    private val snippet = "snippet"
    private val lat = 1.1
    private val lng = 2.2

    private val underTest = MapOperations()

    @Before
    fun setUp() {
        underTest.googleMap = mock()
    }

    @Test
    fun addMarker() {
        underTest.addMarker(title, snippet, lat, lng)

        verify(underTest.googleMap).addMarker(any())
    }

}
