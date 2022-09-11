package com.example.keepmemo.ui.screens.license

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState

@Composable
fun OpenLicenseScreen(
    url: String,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    val state = rememberWebViewState(url = url)

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {},
        topBar = {
            OpenLicenseAppBar(
                title = state.pageTitle ?: "",
                elevation = 0.dp,
                onBackPressed = onBackPressed
            )
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        OpenLicenseScreenContent(
            state = state,
            modifier = contentModifier
        )
    }
}

@Composable
fun OpenLicenseScreenContent(
    state: WebViewState,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        WebView(state = state, modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun OpenLicenseAppBar(
    title: String,
    onBackPressed: () -> Unit,
    elevation: Dp
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
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
