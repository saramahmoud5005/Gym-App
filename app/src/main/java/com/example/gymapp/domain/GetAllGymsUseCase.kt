package com.example.gymapp.domain

import com.example.gymapp.Gym
import com.example.gymapp.GymsRepository

class GetAllGymsUseCase {
    private val gymsRepository = GymsRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()
    suspend operator fun invoke():List<Gym>{
        gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }
}