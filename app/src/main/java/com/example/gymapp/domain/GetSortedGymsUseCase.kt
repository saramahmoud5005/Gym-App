package com.example.gymapp.domain

import com.example.gymapp.Gym
import com.example.gymapp.GymsRepository

class GetSortedGymsUseCase {
    private val gymsRepository = GymsRepository()

    suspend operator fun invoke():List<Gym>{
        return gymsRepository.getGyms()
    }
}