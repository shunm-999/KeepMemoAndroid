package com.example.keepmemo.core.authentication.result

sealed interface PublicKeySignInResult {
    data class Success(val userId: String) : PublicKeySignInResult
    object UserCancel : PublicKeySignInResult
    object ShouldRetry : PublicKeySignInResult
    object NoCredential : PublicKeySignInResult
    object UnknownError : PublicKeySignInResult
}
