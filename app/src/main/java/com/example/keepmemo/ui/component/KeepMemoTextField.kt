package com.example.keepmemo.ui.component

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import com.example.keepmemo.ui.ktx.interceptKey

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeepMemoInputTextField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = false,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
    backgroundTransparent: Boolean = true,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
) {
    CompositionLocalProvider(LocalTextStyle provides textStyle) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                CompositionLocalProvider(
                    LocalTextStyle provides textStyle,
                    LocalContentAlpha provides ContentAlpha.disabled
                ) {
                    Text(placeholder)
                }
            },
            colors = if (backgroundTransparent) {
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            } else {
                TextFieldDefaults.textFieldColors()
            },
            singleLine = singleLine,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (onDone == null) {
                        keyboardController?.hide()
                    } else {
                        onDone()
                    }
                }
            ),
            modifier = modifier
                .interceptKey(Key.Escape) { // dismiss focus when Escape is pressed
                    focusManager.clearFocus()
                }
        )
    }
}
