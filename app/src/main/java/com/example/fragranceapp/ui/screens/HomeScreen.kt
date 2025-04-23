package com.example.fragranceapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fragranceapp.model.Fragrance
import com.example.fragranceapp.ui.components.FragranceItem
import com.example.fragranceapp.viewmodel.FragranceViewModel


// This is our Main Fragrance Screen
// Displays all Fragrances in a List and we have a searchBar that filters for searched Names
// The annotation tells the compiler "we know this API is experimental and accept the risks"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: FragranceViewModel = viewModel() // View Model of Fragrance
    val fragrances by viewModel.fragrances.collectAsState() // Gets the Fragrances and saves them in a val
    Log.d("FragranceResponse", "got response: $fragrances")
    val searchQuery by viewModel.searchQuery.collectAsState() // val for search querie of user
    // Creates Basic Layout
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Fragrance Finder") }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { navController.navigate("main") },
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                    ) {
                        Text("Go Back")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search fragrances...") }
            )
            // Fragrance Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Always filtering depending on what we pass in the textfield
                val filteredFragrances = fragrances.filter {
                    it.fragrance_name.contains(searchQuery, ignoreCase = true)
                }
                // Display the Fragrances
                items(filteredFragrances) { fragrance ->
                    // Fragrance Item is a Component in another file
                    // This is done to display each Fragrance in its State
                    FragranceItem(
                        fragrance = fragrance,
                        onDetailsClick = {
                            navController.navigate("fragranceDetail/${fragrance.fragrance_name}")
                        }
                    )
                }
            }
        }
    }
}


