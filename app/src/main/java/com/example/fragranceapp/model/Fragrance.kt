package com.example.fragranceapp.model
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

// The data model for when you receive frags from API
@Serializable
data class Fragrance(
    val fragrance_name: String,
    val notes: Notes,
    val description: String,
    val imageUrl: String
)

@Serializable
data class Notes(
    val top_notes: List<String>
)