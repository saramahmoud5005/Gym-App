package com.example.gymapp.domain

import com.example.gymapp.Gym
import com.example.gymapp.data.GymsRepository

class ToggleFavouriteStateUseCase {
    private val gymsRepository = GymsRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()
    suspend operator fun invoke(id:Int, oldState: Boolean):List<Gym>{
        val newState = oldState.not()
        gymsRepository.toggleFavouriteGym(id,newState)
        return getSortedGymsUseCase()
    }
}