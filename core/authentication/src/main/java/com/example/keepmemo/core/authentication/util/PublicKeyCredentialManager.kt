package com.example.keepmemo.core.authentication.util

import android.app.Activity
import androidx.credentials.CreatePublicKeyCredentialResponse
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.CreateCredentialInterruptedException
import androidx.credentials.exceptions.publickeycredential.CreatePublicKeyCredentialDomException
import com.example.keepmemo.core.authentication.model.PublicKeyCredentialResponse
import com.example.keepmemo.core.authentication.result.PublicKeyCreateResult
import com.google.gson.Gson

object PublicKeyCredentialManager {

    suspend fun requestCreateCredential(
        userName: String,
        activity: Activity
    ): PublicKeyCreateResult {
        return try {
            val createPublicKeyCredentialRequest = PublicKeyCredentialRequestBuilder(
                userName = userName
            ).build()

            val credentialManager = CredentialManager.create(activity)
            val result = credentialManager.createCredential(
                request = createPublicKeyCredentialRequest,
                activity = activity
            )
            val responseJson = result.data.getString(
                CreatePublicKeyCredentialResponse.BUNDLE_KEY_REGISTRATION_RESPONSE_JSON
            ) ?: throw IllegalStateException("responseJson is null")

            val credentialResponse = Gson().fromJson(
                responseJson,
                PublicKeyCredentialResponse::class.java
            )

            PublicKeyCreateResult.Success(
                userId = credentialResponse.id,
                userName = userName
            )
        } catch (e: CreateCredentialException) {
            handleFailure(e)
        }
    }

    private fun handleFailure(e: CreateCredentialException): PublicKeyCreateResult {
        return when (e) {
            is CreatePublicKeyCredentialDomException -> {
                PublicKeyCreateResult.DomError(type = e.domError.type)
            }
            is CreateCredentialCancellationException -> {
                PublicKeyCreateResult.UserCancel
            }
            is CreateCredentialInterruptedException -> {
                PublicKeyCreateResult.ShouldRetry
            }
            else -> {
                PublicKeyCreateResult.UnknownError
            }
        }
    }
}
