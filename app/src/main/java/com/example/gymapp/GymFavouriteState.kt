package com.example.gymapp

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class GymFavouriteState(
    @ColumnInfo(name = "gym_id")
    val id:Int,
    @ColumnInfo(name = "is_favourite")
    val isFavourite:Boolean =false
)
