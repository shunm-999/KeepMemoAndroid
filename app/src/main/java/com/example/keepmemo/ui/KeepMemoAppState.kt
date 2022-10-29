package com.example.keepmemo.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.keepmemo.R
import com.example.keepmemo.core.navigation.KeepMemoDestination
import com.example.keepmemo.feature.home.navigation.HomeDestination
import com.example.keepmemo.feature.openlicense.navigation.OpenLicenseDestination
import com.example.keepmemo.navigation.DrawerDestination
import timber.log.Timber

@Composable
fun rememberKeepMemoAppState(
    navController: NavHostController = rememberNavController()
): KeepMemoAppState {
    NavigationTrackingSideEffect(navController = navController)
    return remember(navController) {
        KeepMemoAppState(navController)
    }
}

@Stable
class KeepMemoAppState(
    private val navController: NavController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val drawerDestinationList: List<DrawerDestination> = listOf(
        DrawerDestination(
            route = HomeDestination.route,
            destination = HomeDestination.destination,
            icon = Icons.Filled.Home,
            labelId = R.string.title_home
        ),
        DrawerDestination(
            route = OpenLicenseDestination.route,
            destination = OpenLicenseDestination.destination,
            icon = Icons.Filled.Description,
            labelId = R.string.title_license
        )
    )

    fun navigate(destination: KeepMemoDestination, route: String? = null) {
        if (destination is HomeDestination) {
            navController.navigate(route ?: destination.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            navController.navigate(route ?: destination.route)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}

@Composable
private fun NavigationTrackingSideEffect(navController: NavController) {
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            Timber.tag("Navigation Destination Changed").d("${destination.route}")
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}
