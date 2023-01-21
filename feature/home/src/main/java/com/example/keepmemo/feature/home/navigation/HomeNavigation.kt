package com.example.keepmemo.feature.home.navigation

import android.text.TextUtils
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.keepmemo.core.navigation.KeepMemoDestination
import com.example.keepmemo.feature.home.HomeRoute

object HomeDestination : KeepMemoDestination {
    override val route: String = "home"
    override val destination: String = "home"
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    openDrawer: () -> Unit,
    navigateToAddKeep: () -> Unit
) {
    composable(HomeDestination.route) {
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
            navigateToAddKeep = navigateToAddKeep,
            addKeepEvent = addKeepEvent
        )
    }
}
