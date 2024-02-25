package com.example.gymapp.domain

import com.example.gymapp.Gym
import com.example.gymapp.data.GymsRepository
import javax.inject.Inject

class GetSortedGymsUseCase @Inject constructor(
    private val gymsRepository : GymsRepository,
){

    suspend operator fun invoke():List<Gym>{
        return gymsRepository.getGyms()
    }
}