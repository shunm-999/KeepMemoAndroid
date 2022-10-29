package com.example.keepmemo.core.designsystem.component

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.keepmemo.core.designsystem.ktx.interceptKey
import com.example.keepmemo.core.designsystem.theme.disabled

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
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
    isFocused: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) {
    val textFieldValue = remember {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    CompositionLocalProvider(LocalTextStyle provides textStyle) {
        val focusRequesterModifier = Modifier.focusRequester(focusRequester)
        TextField(
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
                onValueChange(it.text)
            },
            placeholder = {
                CompositionLocalProvider(
                    LocalTextStyle provides textStyle,
                    LocalContentColor provides MaterialTheme.colorScheme.onSurface.disabled()
                ) {
                    Text(placeholder)
                }
            },
            colors = if (backgroundTransparent) {
                TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
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
                .then(focusRequesterModifier)
        )
    }

    if (isFocused) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}
