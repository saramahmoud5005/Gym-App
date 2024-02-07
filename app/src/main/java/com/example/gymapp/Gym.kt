package com.example.gymapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//val listOfGym = listOf<Gym>(
//    Gym(1,"name1","address1"),
//    Gym(2,"name2","address2"),
//    Gym(3,"name3","address3"),
//    Gym(4,"name4","address4"),
//    Gym(5,"name1","address1"),
//    Gym(6,"name2","address2"),
//    Gym(7,"name3","address3"),
//    Gym(8,"name4","address4"),
//    Gym(9,"name1","address1"),
//    Gym(10,"name2","address2"),
//    Gym(11,"name3","address3"),
//    Gym(12,"name4","address4"),
//)
@Entity(tableName = "gyms")
data class Gym(
    @PrimaryKey
    @ColumnInfo(name = "gym_id")
    val id:Int,
    @ColumnInfo(name = "gym_name")
    @SerializedName("gym_name")
    val name:String,
    @ColumnInfo(name = "gym_location")
    @SerializedName("gym_location")
    val place:String,
    @SerializedName("is_open")
    val isOpen:Boolean,
    @ColumnInfo(name = "is_favourite")
    val isFavourite:Boolean = false
    )