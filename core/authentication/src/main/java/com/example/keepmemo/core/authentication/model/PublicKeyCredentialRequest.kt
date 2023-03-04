package com.example.keepmemo.core.authentication.model

import com.google.gson.annotations.SerializedName

internal data class PublicKeyCredentialRequest(
    @SerializedName("challenge")
    val challenge: String,
    @SerializedName("rp")
    val relyingPartyEntity: PublicKeyCredentialRelyingPartyEntity,
    @SerializedName("user")
    val userEntity: PublicKeyCredentialUserEntity,
    @SerializedName("pubKeyCredParams")
    val keyCredentialParams: List<PublicKeyCredentialParam>,
    @SerializedName("timeout")
    val timeout: Int,
    @SerializedName("attestation")
    val attestation: String,
    @SerializedName("excludeCredentials")
    val excludeCredentials: List<String>,
    @SerializedName("authenticatorSelection")
    val authenticatorSelectionCriteria: AuthenticatorSelectionCriteria
)

internal data class PublicKeyCredentialRelyingPartyEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

internal data class PublicKeyCredentialUserEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("displayName")
    val displayName: String
)

internal data class PublicKeyCredentialParam(
    @SerializedName("type")
    val type: String,
    @SerializedName("alg")
    val algorithm: Int
) {

    companion object {
        val ES256 = PublicKeyCredentialParam(
            type = "public-key",
            algorithm = -7
        )
        val RS256 = PublicKeyCredentialParam(
            type = "public-key",
            algorithm = -257
        )
    }
}

@Suppress("DataClassPrivateConstructor")
internal data class AuthenticatorSelectionCriteria private constructor(
    @SerializedName("authenticatorAttachment")
    val authenticatorAttachment: String,
    @SerializedName("requireResidentKey")
    val requireResidentKey: Boolean,
    @SerializedName("residentKey")
    val residentKey: String,
    @SerializedName("userVerification")
    val userVerification: String
) {

    companion object {
        fun generateAuthenticatorSelectionCriteria(
            authenticatorAttachment: Authenticator,
            requireResidentKey: Boolean,
            residentKey: Boolean,
            userVerification: Boolean
        ): AuthenticatorSelectionCriteria {
            return AuthenticatorSelectionCriteria(
                authenticatorAttachment = authenticatorAttachment.value,
                requireResidentKey = requireResidentKey,
                residentKey = if (residentKey) {
                    "required"
                } else {
                    ""
                },
                userVerification = if (userVerification) {
                    "required"
                } else {
                    ""
                }
            )
        }
    }

    enum class Authenticator {
        Platform {
            override val value: String = "platform"
        };

        abstract val value: String
    }
}
