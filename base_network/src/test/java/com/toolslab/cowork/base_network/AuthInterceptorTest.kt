package com.toolslab.cowork.base_network

import com.nhaarman.mockito_kotlin.*
import com.toolslab.cowork.base_network.ApiEndpoint.Header.AUTHORIZATION
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

class AuthInterceptorTest {

    private val user = "user"
    private val password = "password"
    private val jwt = Jwt("the jwt token", "", "", "", 0)

    private val mockChain: Interceptor.Chain = mock()
    private val mockRequest: Request = mock()
    private val mockNewRequest: Request = mock()
    private val mockResponse: Response = mock()
    private val mockBuilder: Request.Builder = mock()

    private val underTest = AuthInterceptor()

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
        whenever(mockRequest.header(AUTHORIZATION)).thenReturn("a header's token")

        val response = underTest.intercept(mockChain)

        response shouldEqual mockResponse
        verifyZeroInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.coworkingMapAuthService)
    }

    @Test
    fun interceptWithNullChain() {
        val response = underTest.intercept(null)

        response shouldEqual null
        verifyZeroInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.coworkingMapAuthService)
    }

    @Test
    fun interceptWithAnAuthHeader() {
        whenever(mockChain.request()).thenReturn(mockRequest)
        whenever(mockChain.proceed(mockRequest)).thenReturn(mockResponse)
        whenever(mockResponse.code()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND)
        whenever(mockRequest.header(AUTHORIZATION)).thenReturn("a header's token")
        whenever(mockResponse.request()).thenReturn(mockRequest)

        val response = underTest.intercept(mockChain)

        response shouldEqual mockResponse
        verifyZeroInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.coworkingMapAuthService)
    }

    @Test
    fun addAuthHeaderWhenAlreadyAdded() {
        whenever(mockRequest.header(AUTHORIZATION)).thenReturn("a header's token")

        val request = underTest.addAuthHeader(mockRequest)

        request shouldEqual mockRequest
        verifyZeroInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.coworkingMapAuthService)
    }

    @Test
    fun addAuthHeaderWhenNotYetAdded() {
        val tokenForRequest = underTest.createTokenForRequest(jwt.token)
        whenever(mockRequest.header(AUTHORIZATION)).thenReturn(null)
        whenever(underTest.credentialsStorage.getToken()).thenReturn(jwt.token)
        whenever(mockRequest.newBuilder()).thenReturn(mockBuilder)
        whenever(mockBuilder.header(AUTHORIZATION, tokenForRequest)).thenReturn(mockBuilder)
        whenever(mockBuilder.build()).thenReturn(mockNewRequest)

        val request = underTest.addAuthHeader(mockRequest)

        request shouldEqual mockNewRequest
        verify(underTest.credentialsStorage, times(2)).getToken()
        verifyNoMoreInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.coworkingMapAuthService)
    }

    @Test
    fun addAuthHeaderWhenNotYetAddedAndGetTokenFirst() {
        val token = "a token"
        val tokenForRequest = underTest.createTokenForRequest(token)
        whenever(mockRequest.header(AUTHORIZATION)).thenReturn(null)
        whenever(underTest.credentialsStorage.getToken())
                .thenReturn("") // First check, no token
                .thenReturn("") // Second check, no token
                .thenReturn(token) // Third call, there is a token now
        whenever(underTest.credentialsStorage.getCredentials()).thenReturn(Credentials(user, password))
        whenever(underTest.coworkingMapAuthService.getJwt(user, password)).thenReturn(Single.just(jwt))
        whenever(mockRequest.newBuilder()).thenReturn(mockBuilder)
        whenever(mockBuilder.header(AUTHORIZATION, tokenForRequest)).thenReturn(mockBuilder)
        whenever(mockBuilder.build()).thenReturn(mockNewRequest)

        val request = underTest.addAuthHeader(mockRequest)

        request shouldEqual mockNewRequest
        verify(underTest.credentialsStorage, times(3)).getToken()
        verify(underTest.credentialsStorage).saveToken(jwt.token)
    }


    @Test
    fun addAuthHeaderFromDifferentThreads() {
        val tokenForRequest = underTest.createTokenForRequest(jwt.token)
        whenever(mockRequest.header(AUTHORIZATION)).thenReturn(null)
        whenever(underTest.credentialsStorage.getToken())
                .thenReturn("") // First check, no token
                .thenReturn(jwt.token) // Second check, another thread has saved a token
        whenever(underTest.credentialsStorage.getCredentials()).thenReturn(Credentials(user, password))
        whenever(mockRequest.newBuilder()).thenReturn(mockBuilder)
        whenever(mockBuilder.header(AUTHORIZATION, tokenForRequest)).thenReturn(mockBuilder)
        whenever(mockBuilder.build()).thenReturn(mockNewRequest)

        val request = underTest.addAuthHeader(mockRequest)

        request shouldEqual mockNewRequest
        verify(underTest.credentialsStorage, times(3)).getToken()
        verifyNoMoreInteractions(underTest.credentialsStorage)
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
