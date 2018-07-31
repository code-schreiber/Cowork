package com.toolslab.base_repository

// TODO move all modules to app-specific packages

import com.toolslab.base_repository.exception.ForbiddenException
import com.toolslab.base_repository.exception.NoConnectionException
import com.toolslab.base_repository.exception.NotFoundException
import com.toolslab.base_repository.exception.UnauthorizedException
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
                    else -> Single.error(throwable)
                }
                else -> Single.error(throwable)
            }

}
