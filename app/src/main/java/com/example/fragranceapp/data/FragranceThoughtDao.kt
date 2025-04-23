package com.example.fragranceapp.data

import androidx.room.*
import com.example.fragranceapp.model.FragranceThought
import kotlinx.coroutines.flow.Flow

// Uses Kotlins Room Database for storing our personal thoughts on a fragrance
@Dao
interface FragranceThoughtDao {
    @Query("SELECT * FROM fragrance_thoughts WHERE fragranceName = :fragranceName")
    fun getThoughtForFragrance(fragranceName: String): Flow<FragranceThought?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateThought(thought: FragranceThought)

    @Delete
    suspend fun deleteThought(thought: FragranceThought)
} 