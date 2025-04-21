package com.example.fragranceapp.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fragranceapp.model.Fragrance
import com.example.fragranceapp.viewmodel.FragranceViewModel


@Composable
fun SingleFragrance(navController: NavController, singleFragranceName: String ){
    val viewModel: FragranceViewModel = viewModel()
    val fragrance = viewModel.getFragranceByName(singleFragranceName)

    if (fragrance != null) {
        Column {
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
            Text(
                text = fragrance.description,
                style = MaterialTheme.typography.titleSmall
            )
        }
    } else {
        Text("Fragrance not found")
    }
}