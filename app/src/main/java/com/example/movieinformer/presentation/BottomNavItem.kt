package com.example.movieinformer.presentation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route:String, val icon: ImageVector, val label:String) {
    data object Home : BottomNavItem("Home",Icons.Default.Home, "Home")

    data object Upcoming : BottomNavItem("Upcoming", Icons.Default.PlayArrow, "Upcoming")

    data object Searching : BottomNavItem("Search", Icons.Default.Search, "Search")

}