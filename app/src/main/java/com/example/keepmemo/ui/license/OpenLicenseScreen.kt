package com.example.keepmemo.ui.license

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.keepmemo.R
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun OpenLicenseScreen(
    url: String,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {},
        topBar = {
            OpenLicenseAppBar(
                elevation = 0.dp,
                onBackPressed = onBackPressed
            )
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        OpenLicenseScreenContent(
            url = url,
            modifier = contentModifier
        )
    }
}

@Composable
fun OpenLicenseScreenContent(
    url: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        val state = rememberWebViewState(url = url)
        WebView(state = state, modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun OpenLicenseAppBar(
    onBackPressed: () -> Unit,
    elevation: Dp
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.open_license_appbar_title))
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
