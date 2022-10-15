package com.example.keepmemo.feature.openlicense.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.keepmemo.core.navigation.KeepMemoDestination
import com.example.keepmemo.feature.openlicense.OpenLicenseRoute

object OpenLicenseDestination : KeepMemoDestination {
    override val route: String = "openLicense"
    override val destination: String = "openLicense"
}

fun NavGraphBuilder.openLicenseGraph(
    navController: NavController
) {
    composable(OpenLicenseDestination.route) {
        OpenLicenseRoute(onBackPressed = {
            navController.popBackStack()
        })
    }
}
