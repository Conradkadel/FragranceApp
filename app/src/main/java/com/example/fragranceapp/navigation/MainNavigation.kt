package com.example.fragranceapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fragranceapp.model.Fragrance
import com.example.fragranceapp.ui.theme.screens.MainScreen
import com.example.fragranceapp.ui.theme.screens.FavoritesScreen
import com.example.fragranceapp.ui.theme.screens.HomeScreen
import com.example.fragranceapp.ui.theme.screens.SingleFragrance
import kotlinx.serialization.json.Json

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("favorites") {
            FavoritesScreen(navController)
        }
        composable("mainList") {
            HomeScreen(navController)
        }
        composable(
            route = "singleFragrance/{fragranceName}",
            arguments = listOf(navArgument("fragrance_name") { type = NavType.StringType })
        ) { backStackEntry ->
            // Extract the fragranceName from the route
            val fragranceName = backStackEntry.arguments?.getString("fragrance_name")
            if (fragranceName != null) {
                SingleFragrance(navController = navController, singleFragranceName = fragranceName)
            } else {
                // Handle the case where fragranceName is null (e.g., navigate back)
                Text("Error: Fragrance name not found")
            }
        }
    }

}