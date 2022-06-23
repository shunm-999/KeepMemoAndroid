package com.example.keepmemo

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.example.keepmemo.ui.screens.alarmfullscreen.AlarmAlertFullScreen
import com.example.keepmemo.ui.screens.launch.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        mainActivityViewModel.setIsFullScreen(TextUtils.equals(intent.action, ACTION_FULLSCREEN))

        setContent {

            val isFullScreen by mainActivityViewModel.isFullScreen.collectAsState()

            if (isFullScreen) {
                AlarmAlertFullScreen()
            } else {
                KeepDemoApp(mainActivityViewModel = mainActivityViewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mainActivityViewModel.setIsFullScreen(TextUtils.equals(intent?.action, ACTION_FULLSCREEN))
    }

    companion object {
        const val ACTION_FULLSCREEN = "ACTION_FULLSCREEN"
    }
}
