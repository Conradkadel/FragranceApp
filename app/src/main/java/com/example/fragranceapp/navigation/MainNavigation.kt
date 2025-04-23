package com.example.fragranceapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fragranceapp.ui.screens.FragranceDetailScreen
import com.example.fragranceapp.ui.screens.HomeScreen
import com.example.fragranceapp.ui.screens.FavoritesScreen
import com.example.fragranceapp.ui.screens.MainScreen

@Composable
fun MainNavigation() {
    // Creates the Navigation Controller 
    val navController = rememberNavController()

    // Creaes the Navigation Environment. Creates Routes to Pages.
    // Starts at "main" page
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("favorites") {
            FavoritesScreen(navController)
        }
        composable(
            // This Route takes an Extra argument which is the Name of the fragrance
            // This is done to pass in a Fragrance as a Parameter as each name is unique.
            route = "fragranceDetail/{fragranceName}",
            arguments = listOf(navArgument("fragranceName") { type = NavType.StringType })
            ) {
                // To make sure Argument Fragrance is given to the DetailScreen if not cause an Error.
                // Needs to be done because we have an extra argument in the navigation
                // So we have save navigation and it maintaines the history of the navigations
                backStackEntry ->
            val fragranceName = backStackEntry.arguments?.getString("fragranceName")
            if (fragranceName != null) {
                FragranceDetailScreen(navController = navController, fragranceName = fragranceName)
            } else {
                Text("Error: Fragrance not found")
            }
        }
    }
}