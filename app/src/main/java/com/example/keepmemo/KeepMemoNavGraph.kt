package com.example.keepmemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.keepmemo.ui.home.HomeRoute

@Composable
fun KeepMemoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navigationActions: KeepMemoNavigationActions = rememberKeepMemoNavigationActions(navController),
    openDrawer: () -> Unit = {},
    startDestination: String = KeepMemoNavigation.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(KeepMemoNavigation.Home.route) {
            HomeRoute(
                openDrawer = openDrawer
            )
        }
    }
}
