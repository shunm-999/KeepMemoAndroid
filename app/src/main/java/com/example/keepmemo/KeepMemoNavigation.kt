package com.example.keepmemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class KeepMemoNavigation(val route: String) {
    object Home : KeepMemoNavigation("memoList")
}

@Composable
fun rememberKeepMemoNavigationActions(
    navController: NavHostController = rememberNavController()
): KeepMemoNavigationActions = remember(navController) {
    KeepMemoNavigationActions(navController)
}

class KeepMemoNavigationActions(navController: NavController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(KeepMemoNavigation.Home.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
