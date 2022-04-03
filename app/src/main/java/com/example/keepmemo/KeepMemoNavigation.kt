package com.example.keepmemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class KeepMemoNavigation(val route: String) {
    object Home : KeepMemoNavigation("home")
    object AddOrEditKeep : KeepMemoNavigation("addOrEditKeep?keepId={keepId}") {
        const val QUERY_KET_KEEP_ID = "keepId"
        fun createRoute(keepId: Long) = "addOrEditKeep?$QUERY_KET_KEEP_ID=$keepId"
    }
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
    val navigateToAddKeep: () -> Unit = {
        navController.navigate(KeepMemoNavigation.AddOrEditKeep.route)
    }
    val navigateToEditKeep: (keepId: Long) -> Unit = { keepId ->
        navController.navigate(KeepMemoNavigation.AddOrEditKeep.createRoute(keepId = keepId))
    }
}
