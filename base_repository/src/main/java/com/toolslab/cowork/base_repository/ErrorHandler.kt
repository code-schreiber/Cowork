package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_repository.exception.*
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection.*
import javax.inject.Inject

internal class ErrorHandler @Inject constructor() {

    internal fun <T> handle(throwable: Throwable): Single<T> =
            when (throwable) {
                is IOException -> Single.error(NoConnectionException(throwable))
                is HttpException -> when (throwable.code()) {
                    HTTP_NOT_FOUND -> Single.error(NotFoundException(throwable))
                    HTTP_UNAUTHORIZED -> Single.error(UnauthorizedException(throwable))
                    HTTP_FORBIDDEN -> Single.error(ForbiddenException(throwable))
                    else -> Single.error(RepositoryException(throwable))
                }
                else -> Single.error(RepositoryException(throwable))
            }

    internal fun isTokenExpired(throwable: Throwable) = throwable is HttpException && throwable.code() == HTTP_FORBIDDEN

}
