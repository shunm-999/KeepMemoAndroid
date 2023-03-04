package com.example.keepmemo.core.designsystem.ktx

import androidx.compose.runtime.Immutable

@Immutable
class ImmutableList<E>(list: List<E>) : List<E> by list.toList() {
    companion object {
        fun <T : List<E>, E> T.toImmutableList() = ImmutableList(this)
    }
}
