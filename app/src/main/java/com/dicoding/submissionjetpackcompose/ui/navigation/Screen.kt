package com.dicoding.submissionjetpackcompose.ui.navigation

sealed class Screen(val route:String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Detail : Screen("home/{heroId}") {
        fun createRoute(heroId: Long) = "home/$heroId"
    }
}