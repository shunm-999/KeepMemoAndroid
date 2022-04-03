package com.example.keepmemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.keepmemo.ui.editkeep.AddOrEditKeepRoute
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
                openDrawer = openDrawer,
                navigateToAddKeep = navigationActions.navigateToAddKeep,
                navigateToEditKeep = navigationActions.navigateToEditKeep
            )
        }
        composable(
            KeepMemoNavigation.AddOrEditKeep.route,
            arguments = listOf(
                navArgument(KeepMemoNavigation.AddOrEditKeep.QUERY_KET_KEEP_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val keepId: Long =
                backStackEntry.arguments?.getLong(KeepMemoNavigation.AddOrEditKeep.QUERY_KET_KEEP_ID)
                    ?: -1L
            AddOrEditKeepRoute(
                targetKeepId = keepId,
                editTime = System.currentTimeMillis(),
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}
