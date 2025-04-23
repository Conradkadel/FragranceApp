package com.example.fragranceapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fragranceapp.data.FragranceDatabase
import com.example.fragranceapp.model.FragranceThought
import com.example.fragranceapp.viewmodel.FragranceViewModel
import kotlinx.coroutines.launch


// This is our Detail Screen for each Fragrance when View Details is Pressed
// Takes the Nav Controller and the fragranceName as parameter
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FragranceDetailScreen(
    navController: NavController,
    fragranceName: String
) {
    val viewModel: FragranceViewModel = viewModel() // View Model of Fragrance
    val fragrances by viewModel.fragrances.collectAsState()
    val fragrance = fragrances.find { it.fragrance_name == fragranceName }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { FragranceDatabase.getDatabase(context) }
    val thoughtDao = remember { database.fragranceThoughtDao() }
    
    var personalThought by remember { mutableStateOf("") }

    
    // Load existing thought
    LaunchedEffect(fragranceName) {
        thoughtDao.getThoughtForFragrance(fragranceName).collect { thought ->
            personalThought = thought?.personalThought ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(fragranceName) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (fragrance != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large Image
                AsyncImage(
                    model = fragrance.imageUrl,
                    contentDescription = "Picture of ${fragrance.fragrance_name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Fragrance Name
                Text(
                    text = fragrance.fragrance_name,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Notes Section
                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                val notes = fragrance.notes.top_notes
                Text(
                    text = notes.joinToString(", "),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Personal Thoughts Section
                Text(
                    text = "My Thoughts",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Text input for personal thoughts
                OutlinedTextField(
                    value = personalThought,
                    onValueChange = { personalThought = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = { Text("Write your thoughts about this fragrance...") },
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(
                    onClick = {
                        scope.launch {
                            thoughtDao.insertOrUpdateThought(
                                FragranceThought(
                                    fragranceName = fragranceName,
                                    personalThought = personalThought
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Save")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Thoughts")
                }
            }
        } else {
            // Show error or loading state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Fragrance not found")
            }
        }
    }
}