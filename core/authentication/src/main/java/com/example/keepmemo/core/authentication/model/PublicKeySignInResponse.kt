package com.example.keepmemo.core.authentication.model

import com.google.gson.annotations.SerializedName

internal data class PublicKeySignInResponse(
    @SerializedName("response")
    val clientData: PublicKeySignInResponseClientData,
    @SerializedName("id")
    val id: String,
    @SerializedName("rawId")
    val rawId: String,
    @SerializedName("type")
    val type: String
)

internal data class PublicKeySignInResponseClientData(
    @SerializedName("clientDataJSON")
    val clientDataJSON: String,
    @SerializedName("attestationObject")
    val attestationObject: String,
    @SerializedName("transports")
    val transports: List<String>,
    @SerializedName("userHandle")
    val userHandle: String
)
