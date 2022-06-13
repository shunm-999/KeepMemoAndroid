package com.example.keepmemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class KeepMemoNavigation(val route: String) {
    object Home : KeepMemoNavigation("home")
    object AddOrEditKeep : KeepMemoNavigation("addOrEditKeep?targetId={targetId}") {
        const val QUERY_TARGET_ID = "targetId"
        fun createRoute(targetId: Long) = "addOrEditKeep?$QUERY_TARGET_ID=$targetId"
    }

    object OpenLicense : KeepMemoNavigation("openLicense")
    object Alarm : KeepMemoNavigation("alarm")
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
                // saveState = true
            }
            launchSingleTop = true
            // restoreState = true
        }
    }
    val navigationToLicense: () -> Unit = {
        navController.navigate(KeepMemoNavigation.OpenLicense.route)
    }
    val navigateToAddKeep: () -> Unit = {
        navController.navigate(KeepMemoNavigation.AddOrEditKeep.route)
    }
    val navigateToEditKeep: (targetId: Long) -> Unit = { targetId ->
        navController.navigate(KeepMemoNavigation.AddOrEditKeep.createRoute(targetId = targetId))
    }
    val navigationToAlarm: () -> Unit = {
        navController.navigate(KeepMemoNavigation.Alarm.route)
    }
}
