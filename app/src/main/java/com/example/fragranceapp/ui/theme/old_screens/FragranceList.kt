package com.example.fragranceapp.ui.theme.old_screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fragranceapp.ui.components.FragranceItem
import com.example.fragranceapp.viewmodel.FragranceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: FragranceViewModel = viewModel()
    val fragrances by viewModel.fragrances.collectAsState()
    Log.d("FragranceResponse", "got response: $fragrances")
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fragrance List") },
                actions = {
                    // Home button
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Go to Home"
                        )
                    }
                }
            )
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
                val filteredFragrances = fragrances.filter {
                    it.fragrance_name.contains(searchQuery, ignoreCase = true)
                }

                items(filteredFragrances) { fragrance ->
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

