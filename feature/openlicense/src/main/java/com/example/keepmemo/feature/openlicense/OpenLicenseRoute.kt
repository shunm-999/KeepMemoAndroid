package com.example.keepmemo.feature.openlicense

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun OpenLicenseRoute(
    openLicenseViewModel: OpenLicenseViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by openLicenseViewModel.uiState.collectAsStateWithLifecycle()
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
