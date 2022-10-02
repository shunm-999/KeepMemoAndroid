package com.example.keepmemo.core.designsystem.ktx

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type

/**
 * Intercepts a key event rather than passing it on to children
 */
fun Modifier.interceptKey(key: Key, onKeyEvent: () -> Unit): Modifier {
    return this.onPreviewKeyEvent {
        if (it.key == key && it.type == KeyEventType.KeyUp) {
            onKeyEvent()
            true
        } else {
            it.key == key
        }
    }
}

/**
 * Intercepts a key event rather than passing it on to children
 */
fun Modifier.observeKeyEvent(key: Key, onKeyEvent: () -> Unit): Modifier {
    return this.onKeyEvent {
        if (it.key == key && it.type == KeyEventType.KeyUp) {
            onKeyEvent()
            true
        } else {
            it.key == key
        }
    }
}
