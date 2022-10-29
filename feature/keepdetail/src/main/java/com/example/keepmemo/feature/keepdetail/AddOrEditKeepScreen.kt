package com.example.keepmemo.feature.keepdetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.keepmemo.core.designsystem.component.KeepMemoInputTextField
import com.example.keepmemo.core.designsystem.component.KeepMemoSnackbarHost
import com.example.keepmemo.core.designsystem.theme.KeepMemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditKeepScreen(
    title: String,
    body: String,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    onBackPressed: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        snackbarHost = { KeepMemoSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AddOrEditKeepTopAppBar(
                scrollBehavior = scrollBehavior,
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
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
                textStyle = MaterialTheme.typography.headlineSmall,
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
                textStyle = MaterialTheme.typography.bodyLarge,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddOrEditKeepTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior
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
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("AddOrEditKeepTopAppBar")
@Preview("AddOrEditKeepTopAppBar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddOrEditKeepTopAppBarPreview() {
    KeepMemoTheme {
        AddOrEditKeepTopAppBar(
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            onBackPressed = {}
        )
    }
}
