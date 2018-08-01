package com.toolslab.cowork.base_repository

import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class TokenRepositoryTest {

    private val tokenString = "tokenString"

    private var underTest = TokenRepository()

    @Before
    fun setUp() {
        underTest.cachedToken.token = tokenString
    }

    @Test
    fun getToken() {
        val token = underTest.getToken()

        token shouldEqual tokenString
    }

    @Test
    fun saveToken() {
        val someToken = "some token"

        underTest.saveToken(someToken)

        underTest.getToken() shouldEqual someToken
    }

    @Test
    fun invalidateToken() {
        underTest.isTokenValid() shouldEqual true

        underTest.invalidateToken()

        underTest.isTokenValid() shouldEqual false
    }

    @Test
    fun isTokenValid() {
        val isTokenValid = underTest.isTokenValid()

        isTokenValid shouldEqual true
    }

}
