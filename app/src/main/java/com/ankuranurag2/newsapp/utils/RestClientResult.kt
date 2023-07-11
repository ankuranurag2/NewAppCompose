package com.ankuranurag2.newsapp.utils

data class RestClientResult<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val errorCode: String? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): RestClientResult<T> {
            return RestClientResult(Status.SUCCESS, data, null)
        }

        fun <T> loading(): RestClientResult<T> {
            return RestClientResult(Status.LOADING)
        }

        fun <T> error(
            message: String,
            errorCode: String? = null
        ): RestClientResult<T> {
            return RestClientResult(
                Status.ERROR,
                message = message,
                errorCode = errorCode
            )
        }
    }
}