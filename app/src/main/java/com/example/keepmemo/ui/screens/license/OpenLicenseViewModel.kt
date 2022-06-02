package com.example.keepmemo.ui.screens.license

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class OpenLicenseUiState(
    val url: String = "file:///android_asset/licenses.html"
)

@HiltViewModel
class OpenLicenseViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(OpenLicenseUiState())
    val uiState: StateFlow<OpenLicenseUiState> = _uiState.asStateFlow()
}
