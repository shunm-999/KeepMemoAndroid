package com.example.keepmemo

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.example.keepmemo.ui.screens.alarmfullscreen.AlarmAlertFullScreen
import com.example.keepmemo.ui.screens.launch.MainActivityViewModel
import com.example.keepmemo.util.DeviceUtil
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

            SideEffect {
                if (isFullScreen) {
                    turnScreenOn()
                } else {
                    turnScreenOff()
                }
            }

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

    private fun turnScreenOn() {
        if (DeviceUtil.isOreoMR1Over()) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }

        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
    }

    private fun turnScreenOff() {
        if (DeviceUtil.isOreoMR1Over()) {
            setShowWhenLocked(false)
            setTurnScreenOn(false)
        }

        window.clearFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
    }

    companion object {
        const val ACTION_FULLSCREEN = "ACTION_FULLSCREEN"
    }
}
