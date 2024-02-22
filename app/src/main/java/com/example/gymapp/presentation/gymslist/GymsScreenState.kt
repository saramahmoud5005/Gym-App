package com.example.gymapp.presentation.gymslist

import com.example.gymapp.Gym

data class GymsScreenState(
    val gyms: List<Gym>,
    val isLoading:Boolean,
    val error:String?=null,
)