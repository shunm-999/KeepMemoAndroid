package com.example.keepmemo.core.authentication.result

sealed interface PublicKeyCreateResult {

    data class Success(val userId: String, val userName: String) : PublicKeyCreateResult
    data class DomError(val type: String) : PublicKeyCreateResult
    object UserCancel : PublicKeyCreateResult
    object ShouldRetry : PublicKeyCreateResult
    object UnknownError : PublicKeyCreateResult
}
