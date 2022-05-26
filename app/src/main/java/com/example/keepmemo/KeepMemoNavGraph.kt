package com.example.keepmemo

import android.text.TextUtils
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.keepmemo.ui.editkeep.AddOrEditKeepRoute
import com.example.keepmemo.ui.editkeep.AddOrEditKeepRouteEvent
import com.example.keepmemo.ui.home.HomeRoute
import com.example.keepmemo.ui.license.OpenLicenseRoute

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

            val valueScreenResult = navController.currentBackStackEntry
                ?.savedStateHandle
                ?.getLiveData<String>("addOrEditKeep")?.observeAsState()

            val addKeepEvent = remember(valueScreenResult) {
                if (TextUtils.equals(valueScreenResult?.value, "added")) {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<String>("addOrEditKeep")
                    System.currentTimeMillis().toString()
                } else {
                    ""
                }
            }

            HomeRoute(
                openDrawer = openDrawer,
                navigateToAddKeep = navigationActions.navigateToAddKeep,
                navigateToEditKeep = navigationActions.navigateToEditKeep,
                addKeepEvent = addKeepEvent
            )
        }
        composable(KeepMemoNavigation.OpenLicense.route) {
            OpenLicenseRoute(onBackPressed = {
                navController.popBackStack()
            })
        }
        composable(
            KeepMemoNavigation.AddOrEditKeep.route,
            arguments = listOf(
                navArgument(KeepMemoNavigation.AddOrEditKeep.QUERY_TARGET_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val targetId: Long =
                backStackEntry.arguments?.getLong(KeepMemoNavigation.AddOrEditKeep.QUERY_TARGET_ID)
                    ?: -1L
            AddOrEditKeepRoute(
                targetId = targetId,
                editTime = System.currentTimeMillis(),
                onBackPressed = { event ->
                    if (event is AddOrEditKeepRouteEvent.ADDED) {
                        // 新規作成時
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(
                                "addOrEditKeep", "added"
                            )
                    }
                    navController.popBackStack()
                }
            )
        }
    }
}
