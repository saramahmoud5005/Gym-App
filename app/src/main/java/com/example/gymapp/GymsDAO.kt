package com.example.gymapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GymsDAO {
    @Query("SELECT * FROM gyms")
    suspend fun getGyms():List<Gym>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGyms(gyms:List<Gym>)
}