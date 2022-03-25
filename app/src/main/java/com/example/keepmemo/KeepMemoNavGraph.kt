package com.example.keepmemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.keepmemo.ui.home.HomeRoute
import com.example.keepmemo.ui.home.HomeViewModel

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
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeRoute(homeViewModel = homeViewModel)
        }
    }
}
