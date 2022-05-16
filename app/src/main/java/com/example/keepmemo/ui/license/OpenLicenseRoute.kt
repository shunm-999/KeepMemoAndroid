package com.example.keepmemo.ui.license

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OpenLicenseRoute(
    openLicenseViewModel: OpenLicenseViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by openLicenseViewModel.uiState.collectAsState()
    HomeRoute(
        uiState = uiState,
        onBackPressed = onBackPressed,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun HomeRoute(
    uiState: OpenLicenseUiState,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState
) {
    OpenLicenseScreen(
        url = uiState.url,
        onBackPressed = onBackPressed,
        scaffoldState = scaffoldState
    )
}
