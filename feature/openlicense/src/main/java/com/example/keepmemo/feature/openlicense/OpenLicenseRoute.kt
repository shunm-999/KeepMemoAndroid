package com.example.keepmemo.feature.openlicense

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OpenLicenseRoute(
    openLicenseViewModel: OpenLicenseViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by openLicenseViewModel.uiState.collectAsState()
    OpenLicenseRoute(
        uiState = uiState,
        onBackPressed = onBackPressed,
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun OpenLicenseRoute(
    uiState: OpenLicenseUiState,
    onBackPressed: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    OpenLicenseScreen(
        url = uiState.url,
        onBackPressed = onBackPressed,
        snackbarHostState = snackbarHostState
    )
}
