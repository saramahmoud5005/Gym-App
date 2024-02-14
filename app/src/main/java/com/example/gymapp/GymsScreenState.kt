package com.example.gymapp

data class GymsScreenState(
    val gyms: List<Gym>,
    val isLoading:Boolean,
    val error:String?=null,
)