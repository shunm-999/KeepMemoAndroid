package com.example.keepmemo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.keepmemo.core.navigation.KeepMemoDestination
import com.example.keepmemo.feature.home.navigation.HomeDestination
import com.example.keepmemo.feature.home.navigation.homeGraph
import com.example.keepmemo.feature.keepdetail.navigation.AddOrEditKeepDestination
import com.example.keepmemo.feature.keepdetail.navigation.addOrEditKeepGraph
import com.example.keepmemo.feature.openlicense.navigation.openLicenseGraph

@Composable
fun KeepMemoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigationToDestination: (KeepMemoDestination, String) -> Unit,
    openDrawer: () -> Unit = {},
    onBackClick: () -> Unit = {},
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeGraph(
            navController = navController,
            openDrawer = openDrawer
        ) {
            onNavigationToDestination(
                AddOrEditKeepDestination,
                AddOrEditKeepDestination.route
            )
        }
        addOrEditKeepGraph(
            navController = navController,
            onBackClick = onBackClick
        )
        openLicenseGraph(
            navController = navController
        )
    }
}
