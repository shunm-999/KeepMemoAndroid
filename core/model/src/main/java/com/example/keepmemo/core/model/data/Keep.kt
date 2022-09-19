package com.example.keepmemo.core.model.data

data class Keep(
    val id: Long,
    val title: String,
    val body: String
) {
    companion object {
        val EMPTY = Keep(
            id = -1L,
            title = "",
            body = ""
        )
    }
}
