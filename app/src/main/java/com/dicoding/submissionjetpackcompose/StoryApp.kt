package com.dicoding.submissionjetpackcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.submissionjetpackcompose.ui.component.BottomBar
import com.dicoding.submissionjetpackcompose.ui.navigation.Screen
import com.dicoding.submissionjetpackcompose.ui.screen.detail.DetailScreen
import com.dicoding.submissionjetpackcompose.ui.screen.home.HomeScreen
import com.dicoding.submissionjetpackcompose.ui.screen.profile.ProfileScreen

@Composable
fun StoryApp (
    navController: NavHostController = rememberNavController(),
){
    Scaffold (
        bottomBar = {
            BottomBar(navController)
        }
    ){
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = {
                        heroId ->
                        navController.navigate(Screen.Detail.createRoute(heroId))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("heroId"){ type = NavType.LongType}),

            ) {
                val id = it.arguments?.getLong("heroId") ?: -1L
                DetailScreen(
                    heroId = id,
                    navigateBack = { navController.navigateUp() }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navigateBack = { navController.navigateUp() })
            }
        }
    }
}