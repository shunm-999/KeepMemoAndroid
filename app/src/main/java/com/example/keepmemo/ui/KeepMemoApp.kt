package com.example.keepmemo.ui

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.keepmemo.core.designsystem.theme.KeepMemoTheme
import com.example.keepmemo.core.designsystem.theme.LocalIsShowingDrawer
import com.example.keepmemo.core.ui.CustomAlertDialog
import com.example.keepmemo.core.ui.DialogType
import com.example.keepmemo.feature.home.navigation.HomeDestination
import com.example.keepmemo.navigation.KeepMemoNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun KeepDemoApp(
    mainActivityViewModel: MainActivityViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass
) {
    KeepMemoTheme {
        val systemUiController = rememberSystemUiController()
        val darkIcons = !isSystemInDarkTheme()
        val systemBarColor = MaterialTheme.colorScheme.surface
        SideEffect {
            systemUiController.setSystemBarsColor(systemBarColor, darkIcons = darkIcons)
        }

        val uiState by mainActivityViewModel.uiState.collectAsStateWithLifecycle()

        when (uiState.launchScreen) {
            LaunchScreen.SPLASH -> {
                LaunchContent {
                    mainActivityViewModel.onSplashCompleted()
                }
            }
            LaunchScreen.MAIN -> {
                KeepMemoAppContent(
                    windowSizeClass = windowSizeClass
                )
            }
        }
    }
}

@Composable
private fun LaunchContent(onDismissDialog: (Boolean) -> Unit) {
    CustomAlertDialog(dialogType = DialogType.APPLICATION_PRIVACY_POLICY, onDismissDialog)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KeepMemoAppContent(
    windowSizeClass: WindowSizeClass
) {
    val navController = rememberNavController()
    val navigationAppState = rememberKeepMemoAppState(
        navController = navController,
        windowSizeClass = windowSizeClass
    )

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalIsShowingDrawer provides navigationAppState.shouldShowDrawer
    ) {
        AdaptiveNavigationDrawer(
            shouldShowDrawer = navigationAppState.shouldShowDrawer,
            drawerContent = {
                ModalDrawerSheet {
                    KeepMemoAppDrawer(
                        currentRoute = navigationAppState.currentDestination?.route,
                        destinationList = navigationAppState.drawerDestinationList,
                        navigateToDestination = navigationAppState::navigate,
                        closeDrawer = { coroutineScope.launch { drawerState.close() } },
                        modifier = Modifier
                            .statusBarsPadding()
                            .navigationBarsPadding()
                    )
                }
            },
            drawerState = drawerState,
            gesturesEnabled = navigationAppState.currentDestination?.route
                in listOf(HomeDestination.route)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                KeepMemoNavHost(
                    navController = navController,
                    onNavigationToDestination = navigationAppState::navigate,
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onBackClick = navigationAppState::onBackClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveNavigationDrawer(
    shouldShowDrawer: Boolean,
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    gesturesEnabled: Boolean,
    content: @Composable () -> Unit
) {
    if (shouldShowDrawer) {
        ModalNavigationDrawer(
            drawerContent = drawerContent,
            drawerState = drawerState,
            gesturesEnabled = gesturesEnabled,
            modifier = modifier,
            content = content
        )
    } else {
        PermanentNavigationDrawer(
            drawerContent = drawerContent,
            modifier = modifier,
            content = content
        )
    }
}

class WindowSizePreviewParameterProvider : PreviewParameterProvider<DpSize> {
    override val values = sequenceOf(
        DpSize(500.dp, 800.dp),
        DpSize(640.dp, 800.dp),
        DpSize(1000.dp, 800.dp),
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview("KeepMemoAppContent")
@Preview("KeepMemoAppContent (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewKeepMemoAppContent(
    @PreviewParameter(WindowSizePreviewParameterProvider::class) dpSize: DpSize
) {
    KeepMemoTheme {
        Surface {
            KeepMemoAppContent(
                windowSizeClass = WindowSizeClass.calculateFromSize(dpSize)
            )
        }
    }
}
