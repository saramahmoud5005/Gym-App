package com.example.gymapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDAO {
    @Query("SELECT * FROM gyms")
    suspend fun getGyms():List<Gym>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGyms(gyms:List<Gym>)
    @Update(entity = Gym::class)
    suspend fun updateGym(gymFavouriteState: GymFavouriteState)
    @Query("SELECT * FROM gyms WHERE is_favourite = 1")
    suspend fun getFavouriteGyms(): List<Gym>
    @Update(entity = Gym::class)
    suspend fun updateGyms(gymFavouriteState: List<GymFavouriteState>)
}