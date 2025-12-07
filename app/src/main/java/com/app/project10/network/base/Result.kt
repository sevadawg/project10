package com.app.project10.network.base

sealed class Result<out T : Any, out R> {
    class Success<out T : Any>(val successData: T) : Result<T, Nothing>()
    class Failure<out R : Error>(val errorData: R) : Result<Nothing, R>()

    suspend fun handleResult(
        successBlock: suspend (T) -> Unit = {},
        failureBlock: suspend (R) -> Unit = {},
    ) {
        when (this) {
            is Success -> successBlock(successData)
            is Failure -> failureBlock(errorData)
        }
    }
}