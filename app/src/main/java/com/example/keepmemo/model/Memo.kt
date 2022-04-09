package com.example.keepmemo.model

data class Memo(
    val id: Long,
    val index: Long,
    val isPined: Boolean,
    val keep: Keep
) {
    companion object {
        val EMPTY = Memo(
            id = -1,
            index = 0,
            isPined = false,
            keep = Keep.EMPTY
        )
    }
}
