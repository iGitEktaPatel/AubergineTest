package com.fitbit.api.loaders

import java.lang.Exception

class ResourceLoaderResult<T> private constructor(val result: T?, resultType: ResultType, errorMessage: String?, exception: java.lang.Exception?) {
    val isSuccessful: Boolean
    val exception: java.lang.Exception?
    val errorMessage: String?
    val resultType: ResultType

    enum class ResultType {
        SUCCESS, ERROR, EXCEPTION, LOGGED_OUT
    }

    companion object {
        fun <G> onSuccess(result: G): ResourceLoaderResult<G> {
            return ResourceLoaderResult(result, ResultType.SUCCESS, null, null)
        }

        fun <G> onError(errorMessage: String?): ResourceLoaderResult<G?> {
            return ResourceLoaderResult(null, ResultType.ERROR, errorMessage, null)
        }

        fun <G> onException(exception: Exception): ResourceLoaderResult<G?> {
            var message = exception.message
            if (message == null) {
                message = exception.cause!!.message
            }
            return ResourceLoaderResult(null, ResultType.EXCEPTION, message, exception)
        }

        fun <G> onLoggedOut(): ResourceLoaderResult<G?> {
            return ResourceLoaderResult(null, ResultType.LOGGED_OUT, null, null)
        }
    }

    init {
        isSuccessful = resultType == ResultType.SUCCESS
        this.errorMessage = errorMessage
        this.exception = exception
        this.resultType = resultType
    }
}