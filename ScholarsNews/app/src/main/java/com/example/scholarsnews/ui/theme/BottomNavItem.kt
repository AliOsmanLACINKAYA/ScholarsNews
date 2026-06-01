package com.example.scholarsnews.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Ana Sayfa")
    object Categories : BottomNavItem("categories", Icons.Default.List, "Kategoriler")
    object Favorites : BottomNavItem("favorites", Icons.Default.Favorite, "Favoriler")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profil")
}