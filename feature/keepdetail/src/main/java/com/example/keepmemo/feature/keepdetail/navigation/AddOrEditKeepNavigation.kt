package com.example.keepmemo.feature.keepdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.keepmemo.core.navigation.KeepMemoDestination
import com.example.keepmemo.feature.keepdetail.AddOrEditKeepRoute
import com.example.keepmemo.feature.keepdetail.AddOrEditKeepRouteEvent

object AddOrEditKeepDestination : KeepMemoDestination {
    const val QUERY_TARGET_ID = "targetId"

    override val route: String = "addOrEditKeep?$QUERY_TARGET_ID={targetId}"
    override val destination: String = "addOrEditKeep?$QUERY_TARGET_ID={targetId}"
    fun createRoute(targetId: Long) = "addOrEditKeep?$QUERY_TARGET_ID=$targetId"
}

fun NavGraphBuilder.addOrEditKeepGraph(
    navController: NavController,
    onBackClick: () -> Unit
) {
    composable(
        AddOrEditKeepDestination.route,
        arguments = listOf(
            navArgument(AddOrEditKeepDestination.QUERY_TARGET_ID) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) { backStackEntry ->
        val targetId: Long =
            backStackEntry.arguments?.getLong(AddOrEditKeepDestination.QUERY_TARGET_ID)
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
                            "addOrEditKeep",
                            "added"
                        )
                }
                onBackClick()
            }
        )
    }
}
