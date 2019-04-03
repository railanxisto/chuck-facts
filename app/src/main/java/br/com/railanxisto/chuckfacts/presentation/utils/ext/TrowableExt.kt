package br.com.railanxisto.chuckfacts.presentation.utils.ext

import retrofit2.HttpException

fun Throwable.getRestErrorMessage(): String {
    if (this is HttpException) {
        when (code()) {
            in 400..499 -> return "Some app error occurred"
            in 500..599 -> return "Some server error occurred"
            else -> return "An unexpected error ocurred"
        }
    } else {
        return "An unexpected error ocurred"
    }
}