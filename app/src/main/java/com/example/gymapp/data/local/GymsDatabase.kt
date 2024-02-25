package com.example.gymapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymapp.Gym

@Database(
    entities = [Gym::class],
    version = 1,
    exportSchema = false
)
abstract class GymsDatabase:RoomDatabase() {
    abstract val dao: GymsDAO
}