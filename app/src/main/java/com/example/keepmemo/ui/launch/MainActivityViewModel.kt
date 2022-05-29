package com.example.keepmemo.ui.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LaunchScreen {
    SPLASH,
    MAIN
}

data class LaunchUiState(
    val launchScreen: LaunchScreen = LaunchScreen.SPLASH
)

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LaunchUiState())
    val uiState: StateFlow<LaunchUiState> = _uiState.asStateFlow()

    private val _launchScreen = MutableStateFlow<LaunchScreen>(LaunchScreen.SPLASH)

    init {
        viewModelScope.launch {
            _launchScreen.collect { launchScreen ->
                _uiState.value = LaunchUiState(launchScreen = launchScreen)
            }
        }
    }

    fun onSplashCompleted() {
        _launchScreen.value = LaunchScreen.MAIN
    }
}
