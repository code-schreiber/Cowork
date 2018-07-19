package com.toolslab.cowork.network

import com.toolslab.cowork.network.exception.ForbiddenException
import com.toolslab.cowork.network.exception.NoConnectionException
import com.toolslab.cowork.network.exception.NotFoundException
import com.toolslab.cowork.network.exception.UnauthorizedException
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection.*
import javax.inject.Inject

internal class HttpErrorHandler @Inject constructor() {

    fun <T> handle(throwable: Throwable): Single<T> {
        return when (throwable) {
            is IOException -> Single.error(NoConnectionException(throwable))
            is HttpException -> when (throwable.code()) {
                HTTP_NOT_FOUND -> Single.error(NotFoundException(throwable))
                HTTP_UNAUTHORIZED -> Single.error(UnauthorizedException(throwable))
                HTTP_FORBIDDEN -> Single.error(ForbiddenException(throwable))
                else -> Single.error(throwable)
            }
            else -> Single.error(throwable)
        }
    }

    fun isTokenExpired(throwable: Throwable) = throwable is HttpException && throwable.code() == HTTP_FORBIDDEN

}
