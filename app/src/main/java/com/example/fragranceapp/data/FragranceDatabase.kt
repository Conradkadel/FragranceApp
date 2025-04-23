package com.example.fragranceapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fragranceapp.model.FragranceThought

@Database(entities = [FragranceThought::class], version = 1)
abstract class FragranceDatabase : RoomDatabase() {
    abstract fun fragranceThoughtDao(): FragranceThoughtDao

    companion object {
        @Volatile
        private var INSTANCE: FragranceDatabase? = null

        fun getDatabase(context: Context): FragranceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FragranceDatabase::class.java,
                    "fragrance_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 