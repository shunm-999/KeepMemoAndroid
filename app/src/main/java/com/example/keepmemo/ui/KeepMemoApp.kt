package com.example.keepmemo.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.keepmemo.core.designsystem.theme.KeepMemoTheme
import com.example.keepmemo.core.ui.CustomAlertDialog
import com.example.keepmemo.core.ui.DialogType
import com.example.keepmemo.feature.home.navigation.HomeDestination
import com.example.keepmemo.navigation.KeepMemoNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun KeepDemoApp(
    mainActivityViewModel: MainActivityViewModel = hiltViewModel()
) {
    KeepMemoTheme {
        val systemUiController = rememberSystemUiController()
        val darkIcons = isSystemInDarkTheme()
        val systemBarColor = MaterialTheme.colorScheme.surface
        SideEffect {
            systemUiController.setSystemBarsColor(systemBarColor, darkIcons = darkIcons)
        }

        val uiState by mainActivityViewModel.uiState.collectAsState()

        when (uiState.launchScreen) {
            LaunchScreen.SPLASH -> {
                LaunchContent {
                    mainActivityViewModel.onSplashCompleted()
                }
            }
            LaunchScreen.MAIN -> {
                KeepMemoAppContent()
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
private fun KeepMemoAppContent() {
    val navController = rememberNavController()
    val navigationAppState = rememberKeepMemoAppState(navController)

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KeepMemoTheme {
        Greeting("Android")
    }
}
