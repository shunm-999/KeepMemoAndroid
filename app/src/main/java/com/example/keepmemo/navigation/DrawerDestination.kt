package com.example.keepmemo.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.keepmemo.core.navigation.KeepMemoDestination

data class DrawerDestination(
    override val route: String,
    override val destination: String,
    val icon: ImageVector,
    @StringRes val labelId: Int
) : KeepMemoDestination
