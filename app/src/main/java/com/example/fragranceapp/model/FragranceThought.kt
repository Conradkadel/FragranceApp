package com.example.fragranceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// The model where we store each of our own thoughts on a fragrance
@Entity(tableName = "fragrance_thoughts")
data class FragranceThought(
    @PrimaryKey
    val fragranceName: String,
    var personalThought: String
) 