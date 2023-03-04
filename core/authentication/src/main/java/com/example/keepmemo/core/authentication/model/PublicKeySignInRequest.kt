package com.example.keepmemo.core.authentication.model

import com.google.gson.annotations.SerializedName

internal data class PublicKeySignInRequest(
    @SerializedName("challenge")
    val challenge: String,
    @SerializedName("allowCredentials")
    val allowCredentials: List<String>,
    @SerializedName("timeout")
    val timeout: Int,
    @SerializedName("userVerification")
    val userVerification: String,
    @SerializedName("rpId")
    val rpId: String
)
