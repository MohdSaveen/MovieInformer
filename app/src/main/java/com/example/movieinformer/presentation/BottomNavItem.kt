package com.example.movieinformer.presentation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route:String, val selectedIcon: ImageVector,val unselectedIcon: ImageVector, val label:String) {
    data object Home : BottomNavItem(route = "Home",Icons.Filled.Home,Icons.Outlined.Home, "Home")

    data object Upcoming : BottomNavItem("Upcoming", Icons.Filled.PlayArrow,Icons.Outlined.PlayArrow, "Upcoming")

    data object Searching : BottomNavItem("Search", Icons.Default.Search, Icons.Outlined.Search,"Search")

}