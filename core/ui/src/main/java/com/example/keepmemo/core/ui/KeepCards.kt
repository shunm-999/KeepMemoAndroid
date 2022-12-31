package com.example.keepmemo.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keepmemo.core.designsystem.component.KeepMemoInputTextField
import com.example.keepmemo.core.designsystem.preview.UiModePreviews
import com.example.keepmemo.core.designsystem.theme.KeepMemoTheme
import com.example.keepmemo.core.model.data.Keep
import com.example.keepmemo.core.model.data.Memo

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun KeepCard(
    title: String,
    body: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    isSelected: Boolean,
    isFullScreen: Boolean,
    modifier: Modifier = Modifier,
    onTitleChange: (String) -> Unit = {},
    onBodyChange: (String) -> Unit = {},
) {

    Card(
        border = if (isFullScreen) {
            null
        } else {
            if (isSelected) {
                BorderStroke(
                    3.dp,
                    MaterialTheme.colorScheme.primary
                )
            } else {
                BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.1f
                )
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = if (isFullScreen) {
            modifier
                .fillMaxSize()
        } else {
            modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
        }.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        )
    ) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        val localDensity = LocalDensity.current
        val cardHeight = remember {
            mutableStateOf(0.dp)
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    cardHeight.value = with(localDensity) {
                        it.size.height.toDp()
                    }
                }
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {
                KeepMemoInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    value = title,
                    onValueChange = onTitleChange,
                    placeholder = if (isFullScreen) {
                        stringResource(id = R.string.placeholder_keep_title)
                    } else {
                        ""
                    },
                    singleLine = isFullScreen,
                    readOnly = !isFullScreen,
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    focusManager = focusManager,
                    focusRequester = remember { FocusRequester() },
                    keyboardController = keyboardController
                )

                Spacer(modifier = Modifier.height(8.dp))

                KeepMemoInputTextField(
                    modifier = if (isFullScreen) {
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    } else {
                        Modifier
                            .fillMaxWidth()
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    value = body,
                    onValueChange = onBodyChange,
                    placeholder = if (isFullScreen) {
                        stringResource(id = R.string.placeholder_keep_body)
                    } else {
                        ""
                    },
                    isFocused = !isFullScreen,
                    readOnly = !isFullScreen,
                    focusManager = focusManager,
                    focusRequester = remember { FocusRequester() },
                    keyboardController = keyboardController
                )
            }
            if (!isFullScreen) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight.value)
                        .combinedClickable(
                            onClick = onClick,
                            onLongClick = onLongClick
                        )
                ) {
                }
            }
        }
    }
}

private class FakeMemoProvider : PreviewParameterProvider<Memo> {
    override val values = sequenceOf(
        Memo(
            id = 1,
            index = 1,
            isPined = false,
            keep = Keep(
                id = 1,
                title = "タイトル",
                body = """
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                """.trimIndent()
            )
        ),
        Memo(
            id = 2,
            index = 2,
            isPined = true,
            keep = Keep(
                id = 2,
                title = "タイトル",
                body = """
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                """.trimIndent()
            )
        ),
        Memo(
            id = 3,
            index = 3,
            isPined = false,
            keep = Keep(
                id = 3,
                title = "タイトル",
                body = """
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                """.trimIndent()
            )
        )
    )
}

@UiModePreviews
@Composable
fun KeepCardPreview(
    @PreviewParameter(FakeMemoProvider::class) memo: Memo
) {
    KeepMemoTheme {
        Column {
            KeepCard(
                title = memo.keep.title,
                body = memo.keep.body,
                onClick = {},
                onLongClick = {},
                isSelected = memo.isPined,
                isFullScreen = false
            )
        }
    }
}
