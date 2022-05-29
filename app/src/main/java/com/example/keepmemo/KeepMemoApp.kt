package com.example.keepmemo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.keepmemo.ui.component.AppDrawer
import com.example.keepmemo.ui.component.CustomAlertDialog
import com.example.keepmemo.ui.component.DialogType
import com.example.keepmemo.ui.launch.LaunchScreen
import com.example.keepmemo.ui.launch.MainActivityViewModel
import com.example.keepmemo.ui.theme.KeepMemoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun KeepDemoApp(
    mainActivityViewModel: MainActivityViewModel = hiltViewModel()
) {
    KeepMemoTheme {
        val systemUiController = rememberSystemUiController()
        val darkIcons = MaterialTheme.colors.isLight
        val systemBarColor = MaterialTheme.colors.surface
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
                var openDialog by remember { mutableStateOf(true) }

                val storagePermissionState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )
                if (storagePermissionState.allPermissionsGranted) {
                    KeepMemoAppContent()
                } else {
                    if (openDialog) {
                        CustomAlertDialog(dialogType = DialogType.STORAGE_PERMISSIONS) {
                            openDialog = false
                            storagePermissionState.launchMultiplePermissionRequest()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LaunchContent(onDismissDialog: (Boolean) -> Unit) {
    CustomAlertDialog(dialogType = DialogType.APPLICATION_PRIVACY_POLICY, onDismissDialog)
}

@Composable
private fun KeepMemoAppContent() {
    val navController = rememberNavController()
    val navigationActions = rememberKeepMemoNavigationActions(navController)

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: KeepMemoNavigation.Home.route

    ModalDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToHome = navigationActions.navigateToHome,
                navigateToLicense = navigationActions.navigationToLicense,
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
            )
        },
        drawerState = drawerState,
        gesturesEnabled = currentRoute in listOf(KeepMemoNavigation.Home.route)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            KeepMemoNavGraph(
                navController = navController,
                navigationActions = navigationActions,
                openDrawer = { coroutineScope.launch { drawerState.open() } }
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
