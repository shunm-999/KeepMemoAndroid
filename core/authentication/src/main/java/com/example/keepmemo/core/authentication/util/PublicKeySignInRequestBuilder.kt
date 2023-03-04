package com.example.keepmemo.core.authentication.util

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPublicKeyCredentialOption
import com.example.keepmemo.core.authentication.constant.RP_ID
import com.example.keepmemo.core.authentication.model.PublicKeySignInRequest
import com.google.gson.Gson
import java.util.UUID

internal data class PublicKeySignInRequestBuilder(
    val timeout: Int = 1800000
) {

    private val rpId = RP_ID
    private val userVerification = "required"

    private val preferImmediatelyAvailableCredentials: Boolean = false

    fun build(): GetCredentialRequest {

        val challenge = UUID.randomUUID().toString()
        val publicKeySignInRequest = PublicKeySignInRequest(
            challenge = challenge,
            allowCredentials = emptyList(),
            timeout = timeout,
            userVerification = userVerification,
            rpId = rpId
        )

        val requestJson = Gson().toJson(publicKeySignInRequest)
        val getPublicKeyCredentialOption = GetPublicKeyCredentialOption(
            requestJson = requestJson,
            preferImmediatelyAvailableCredentials = preferImmediatelyAvailableCredentials
        )
        return GetCredentialRequest(
            listOf(getPublicKeyCredentialOption)
        )
    }
}
