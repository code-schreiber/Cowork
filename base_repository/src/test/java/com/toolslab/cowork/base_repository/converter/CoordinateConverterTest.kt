package com.toolslab.cowork.base_repository.converter

import org.amshove.kluent.shouldEqual
import org.junit.Test

class CoordinateConverterTest {

    private val underTest = CoordinateConverter()

    @Test
    fun convert() {
        underTest.convert("1.1") shouldEqual 1.1
    }

    @Test
    fun convertEmptyString() {
        underTest.convert("") shouldEqual underTest.default
    }

    @Test
    fun convertInvalidNumber() {
        underTest.convert("a") shouldEqual underTest.default
    }

}
