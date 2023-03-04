package com.example.keepmemo.core.authentication.util

import androidx.credentials.CreatePublicKeyCredentialRequest
import com.example.keepmemo.core.authentication.constant.RP_ID
import com.example.keepmemo.core.authentication.constant.RP_NAME
import com.example.keepmemo.core.authentication.model.AuthenticatorSelectionCriteria
import com.example.keepmemo.core.authentication.model.PublicKeyCredentialParam
import com.example.keepmemo.core.authentication.model.PublicKeyCredentialRelyingPartyEntity
import com.example.keepmemo.core.authentication.model.PublicKeyCredentialRequest
import com.example.keepmemo.core.authentication.model.PublicKeyCredentialUserEntity
import com.google.gson.Gson
import java.util.UUID

internal data class PublicKeyCredentialRequestBuilder(
    val userName: String = "",
    val timeout: Int = 1800000
) {
    private val keyCredentialRelyingPartyEntity = PublicKeyCredentialRelyingPartyEntity(
        id = RP_ID,
        name = RP_NAME
    )
    private val keyCredentialParams: List<PublicKeyCredentialParam> = listOf(
        PublicKeyCredentialParam.ES256,
        PublicKeyCredentialParam.RS256
    )

    private val attestation = "none"
    private val excludeCredentials: List<String> = emptyList()
    private val authenticatorSelectionCriteria =
        AuthenticatorSelectionCriteria.generateAuthenticatorSelectionCriteria(
            authenticatorAttachment = AuthenticatorSelectionCriteria.Authenticator.Platform,
            requireResidentKey = true,
            residentKey = true,
            userVerification = true
        )

    private val preferImmediatelyAvailableCredentials: Boolean = false

    fun build(): CreatePublicKeyCredentialRequest {
        val challenge = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()

        val publicKeyCredentialRequest = PublicKeyCredentialRequest(
            challenge = challenge,
            relyingPartyEntity = keyCredentialRelyingPartyEntity,
            userEntity = PublicKeyCredentialUserEntity(
                id = userId,
                name = userName,
                displayName = userName
            ),
            keyCredentialParams = keyCredentialParams,
            timeout = timeout,
            attestation = attestation,
            excludeCredentials = excludeCredentials,
            authenticatorSelectionCriteria = authenticatorSelectionCriteria
        )

        val requestJson = Gson().toJson(publicKeyCredentialRequest)
        return CreatePublicKeyCredentialRequest(
            requestJson = requestJson,
            preferImmediatelyAvailableCredentials = preferImmediatelyAvailableCredentials
        )
    }
}
