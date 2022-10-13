package com.example.keepmemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.keepmemo.core.navigation.KeepMemoDestination

object HomeDestination : KeepMemoDestination {
    override val route: String = "home"
    override val destination: String = "home"
}

object AddOrEditKeepDestination : KeepMemoDestination {
    const val QUERY_TARGET_ID = "targetId"

    override val route: String = "addOrEditKeep?$QUERY_TARGET_ID={targetId}"
    override val destination: String = "addOrEditKeep?$QUERY_TARGET_ID={targetId}"
    fun createRoute(targetId: Long) = "addOrEditKeep?$QUERY_TARGET_ID=$targetId"
}

object OpenLicenseDestination : KeepMemoDestination {
    override val route: String = "openLicense"
    override val destination: String = "openLicense"
}

@Composable
fun rememberKeepMemoNavigationActions(
    navController: NavHostController = rememberNavController()
): KeepMemoNavigationActions = remember(navController) {
    KeepMemoNavigationActions(navController)
}

class KeepMemoNavigationActions(navController: NavController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(HomeDestination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                // saveState = true
            }
            launchSingleTop = true
            // restoreState = true
        }
    }
    val navigationToLicense: () -> Unit = {
        navController.navigate(OpenLicenseDestination.route)
    }
    val navigateToAddKeep: () -> Unit = {
        navController.navigate(AddOrEditKeepDestination.route)
    }
    val navigateToEditKeep: (targetId: Long) -> Unit = { targetId ->
        navController.navigate(AddOrEditKeepDestination.createRoute(targetId = targetId))
    }
}
