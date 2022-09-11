package com.example.keepmemo.ui.screens.editkeep

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.keepmemo.R
import com.example.keepmemo.ui.component.KeepMemoInputTextField
import com.example.keepmemo.ui.theme.KeepMemoTheme

@Composable
fun AddOrEditKeepScreen(
    title: String,
    body: String,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {},
        topBar = {
            AddOrEditKeepTopAppBar(
                elevation = 0.dp,
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        AddOrEditKeepScreenContent(
            title = title,
            body = body,
            onTitleChange = onTitleChange,
            onBodyChange = onBodyChange,
            modifier = contentModifier
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddOrEditKeepScreenContent(
    title: String,
    body: String,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        Column {
            KeepMemoInputTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.h5,
                value = title,
                onValueChange = onTitleChange,
                placeholder = stringResource(id = R.string.placeholder_keep_title),
                singleLine = true,
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                focusManager = focusManager,
                focusRequester = remember { FocusRequester() },
                keyboardController = keyboardController
            )

            Spacer(modifier = Modifier.height(8.dp))

            KeepMemoInputTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textStyle = MaterialTheme.typography.body1,
                value = body,
                onValueChange = onBodyChange,
                placeholder = stringResource(id = R.string.placeholder_keep_body),
                isFocused = true,
                focusManager = focusManager,
                focusRequester = remember { FocusRequester() },
                keyboardController = keyboardController
            )
        }
    }
}

@Composable
private fun AddOrEditKeepTopAppBar(
    elevation: Dp,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation
    )
}

@Preview("AddOrEditKeepScreen")
@Preview("AddOrEditKeepScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddOrEditKeepScreenPreview() {
    KeepMemoTheme {
        AddOrEditKeepScreen(
            title = "title",
            body = """
                body1body1body1body1body1body1body1body1body1body1
                body1body1body1body1body1body1body1body1body1body1
                body1body1body1body1body1body1body1body1body1body1
                body1body1body1body1body1body1body1body1body1body1
                body1body1body1body1body1body1body1body1body1body1
                body1body1body1body1body1body1body1body1body1body1
            """.trimIndent(),
            onTitleChange = {},
            onBodyChange = {},
            onBackPressed = {},
            scaffoldState = rememberScaffoldState()
        )
    }
}

@Preview("AddOrEditKeepTopAppBar")
@Preview("AddOrEditKeepTopAppBar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddOrEditKeepTopAppBarPreview() {
    KeepMemoTheme {
        AddOrEditKeepTopAppBar(
            elevation = 0.dp,
            onBackPressed = {}
        )
    }
}
