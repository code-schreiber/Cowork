package com.toolslab.cowork.base_network

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.cowork.base_network.model.Jwt
import com.toolslab.cowork.base_network.storage.Credentials
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class ReauthenticateInterceptorTest {

    private val jwt = Jwt("a token", "", "", "", 0)

    private val mockChain: Interceptor.Chain = mock()
    private val mockRequest: Request = mock()
    private val mockResponse: Response = mock()
    private val mockBuilder: Request.Builder = mock()

    private val underTest = ReauthenticateInterceptor()

    @Before
    fun setUp() {
        underTest.credentialsStorage = mock()
        underTest.coworkingMapAuthService = mock()
    }

    @Test
    fun interceptHappyPath() {
        whenever(mockChain.request()).thenReturn(mockRequest)
        whenever(mockChain.proceed(mockRequest)).thenReturn(mockResponse)
        whenever(mockResponse.code()).thenReturn(HttpURLConnection.HTTP_OK)

        val response = underTest.intercept(mockChain)

        response shouldEqual mockResponse
        verifyZeroInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.coworkingMapAuthService)
    }

    @Test
    fun intercept() {
        val user = "user"
        val password = "password"
        val tokenForRequest = underTest.createTokenForRequest(jwt.token)

        whenever(mockChain.request()).thenReturn(mockRequest)
        whenever(mockChain.proceed(mockRequest)).thenReturn(mockResponse)
        whenever(mockResponse.code()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND)
        whenever(mockResponse.request()).thenReturn(mockRequest)
        whenever(mockRequest.newBuilder()).thenReturn(mockBuilder)
        whenever(mockBuilder.header(ApiEndpoint.Header.AUTHORIZATION, tokenForRequest)).thenReturn(mockBuilder)
        whenever(mockBuilder.build()).thenReturn(mockRequest)
        whenever(underTest.credentialsStorage.getCredentials()).thenReturn(Credentials(user, password))
        whenever(underTest.coworkingMapAuthService.getJwt(user, password)).thenReturn(Single.just(jwt))
        whenever(underTest.credentialsStorage.getToken()).thenReturn(jwt.token)

        val response = underTest.intercept(mockChain)

        verify(underTest.credentialsStorage).saveToken(jwt.token)
        response shouldEqual mockResponse
    }

    @Test
    fun interceptWithNullChain() {
        val response = underTest.intercept(null)

        response shouldEqual null
        verifyZeroInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.coworkingMapAuthService)
    }

    @Test
    fun createTokenForRequest() {
        val token = "a token"
        val expectedTokenForRequest = "Bearer a token"

        val tokenForRequest = underTest.createTokenForRequest(token)

        tokenForRequest shouldEqual expectedTokenForRequest
    }

}
