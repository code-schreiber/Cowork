package com.toolslab.cowork.base_repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.shouldEqual
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection

class ErrorHandlerTest {

    private val mockThrowable: HttpException = mock()

    private val underTest = ErrorHandler()

    @Test
    fun isTokenExpired() {
        whenever(mockThrowable.code()).thenReturn(HttpURLConnection.HTTP_FORBIDDEN)

        val isTokenExpired = underTest.isTokenExpired(mockThrowable)

        isTokenExpired shouldEqual true
    }

    @Test
    fun isTokenNotExpired() {
        val throwable = Exception()

        val isTokenExpired = underTest.isTokenExpired(throwable)

        isTokenExpired shouldEqual false
    }

}
