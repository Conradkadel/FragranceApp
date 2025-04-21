package com.example.fragranceapp.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fragranceapp.model.Fragrance
import com.example.fragranceapp.viewmodel.FragranceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: FragranceViewModel = viewModel()
    val fragrances by viewModel.fragrances.collectAsState()
    Log.d("FragranceResponse", "got response: $fragrances")
    val searchQuery by viewModel.searchQuery.collectAsState()
    val myCollection by viewModel.myCollection.collectAsState()
    Log.d("FragranceResponse", "got response: $myCollection")
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    Text("Main List Screen")
    Button(
        onClick = { navController.navigateUp() },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Back")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fragrance Finder") }
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
            // Fragrance List
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filteredFragrances = fragrances.filter {
                    it.fragrance_name.contains(searchQuery, ignoreCase = true)

                }

                items(filteredFragrances) { fragrance ->
                    FragranceItem(
                        fragrance = fragrance,
                        onAddToCollection = {
                            //viewModel.addToCollection(fragrance)
                            snackbarMessage = "${fragrance.fragrance_name} added to collection"
                            showSnackbar = true
                        },
                        onRemoveFromCollection = {
                            //viewModel.removeFromCollection(fragrance)
                            snackbarMessage = "${fragrance.fragrance_name} removed from collection"
                            showSnackbar = true
                        }
                    )
                }
            }

        }
        // Snackbar
        if (showSnackbar) {
            LaunchedEffect(showSnackbar) {
                showSnackbar = false
            }
            Snackbar(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(snackbarMessage)
            }
        }
    }
}


@Composable
fun FragranceItem(
    fragrance: Fragrance,
    onAddToCollection: () -> Unit,
    onRemoveFromCollection: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    //Picture
                    AsyncImage(
                        model = fragrance.imageUrl,
                        contentDescription = "Picture of ${fragrance.fragrance_name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)

                    )

                    Text(
                        text = fragrance.fragrance_name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    //Button(onClick = {navController.navigate()}) { }
                }
            }
            if (true) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Notes: ${fragrance.notes}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


