package com.example.fragranceapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fragranceapp.model.Fragrance


// This is a Composable we need in order to display our Fragrance in the FragranceList
// Creates a Card for each Fragrance
// We pass it the Fragrance instance and the onDetailsClick function which is our routing to the Details Screen
@Composable
fun FragranceItem(
    fragrance: Fragrance,
    onDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)  // Increased height to accommodate button
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Picture - Fixed height
                AsyncImage(
                    model = fragrance.imageUrl,
                    contentDescription = "Picture of ${fragrance.fragrance_name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)  // Fixed height for image
                )

                Spacer(modifier = Modifier.height(8.dp))
                
                // Title
                Text(
                    text = fragrance.fragrance_name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2, // max number of lines to display
                    overflow = TextOverflow.Ellipsis, // if text is to long it will be truncated to ...
                    modifier = Modifier.height(40.dp)  // Fixed height for title (2 lines)
                )
                // Notes
                Text(
                    text = "Notes: ${fragrance.notes.top_notes.joinToString(", ")}", // Joins the List to a string
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.height(40.dp)  // Fixed height for notes (2 lines)
                )
            }
            
            // Details Button
            Button(
                onClick = onDetailsClick, // Routing to Details Screen
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("View Details")
            }
        }
    }
} 