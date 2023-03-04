package com.example.keepmemo.core.authentication.model

import com.google.gson.annotations.SerializedName

internal data class PublicKeyCredentialResponse(
    @SerializedName("response")
    val clientData: PublicKeyCredentialResponseClientData,
    @SerializedName("authenticatorAttachment")
    val authenticatorAttachment: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("rawId")
    val rawId: String,
    @SerializedName("type")
    val type: String
)

internal data class PublicKeyCredentialResponseClientData(
    @SerializedName("clientDataJSON")
    val clientDataJSON: String,
    @SerializedName("attestationObject")
    val attestationObject: String,
    @SerializedName("transports")
    val transports: List<String>
)
