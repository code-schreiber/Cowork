package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_repository.exception.NoConnectionException
import com.toolslab.cowork.base_repository.exception.RepositoryException
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject

// TODO write new tests
internal class ErrorHandler @Inject constructor() {

    internal fun <T> handle(throwable: Throwable): Single<T> =
            when (throwable) {
                is IOException -> Single.error(NoConnectionException(throwable))
                else -> Single.error(RepositoryException(throwable))
            }

}
