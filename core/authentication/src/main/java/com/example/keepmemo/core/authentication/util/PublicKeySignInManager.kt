package com.example.keepmemo.core.authentication.util

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.GetCredentialInterruptedException
import androidx.credentials.exceptions.NoCredentialException
import com.example.keepmemo.core.authentication.model.PublicKeySignInResponse
import com.example.keepmemo.core.authentication.result.PublicKeySignInResult
import com.google.gson.Gson

object PublicKeySignInManager {

    suspend fun requestSignIn(
        activity: Activity
    ): PublicKeySignInResult {
        return try {
            val signInPublicKeyRequest = PublicKeySignInRequestBuilder().build()

            val credentialManager = CredentialManager.create(activity)
            val result = credentialManager.getCredential(
                request = signInPublicKeyRequest,
                activity = activity,
            )

            val credential = result.credential

            if (credential !is PublicKeyCredential) {
                return PublicKeySignInResult.UnknownError
            }

            val responseJson = credential.authenticationResponseJson

            val signInResponse = Gson().fromJson(
                responseJson,
                PublicKeySignInResponse::class.java
            )

            PublicKeySignInResult.Success(
                userId = signInResponse.id
            )
        } catch (e: GetCredentialException) {
            handleFailure(e)
        }
    }

    private fun handleFailure(e: GetCredentialException): PublicKeySignInResult {
        return when (e) {
            is GetCredentialCancellationException -> {
                PublicKeySignInResult.UserCancel
            }
            is GetCredentialInterruptedException -> {
                PublicKeySignInResult.ShouldRetry
            }
            is NoCredentialException -> {
                PublicKeySignInResult.NoCredential
            }
            else -> {
                PublicKeySignInResult.UnknownError
            }
        }
    }
}
