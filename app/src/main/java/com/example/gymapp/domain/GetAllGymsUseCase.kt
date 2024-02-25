package com.example.gymapp.domain

import com.example.gymapp.Gym
import com.example.gymapp.data.GymsRepository
import javax.inject.Inject

class GetAllGymsUseCase @Inject constructor(
    private val gymsRepository : GymsRepository,
    private val getSortedGymsUseCase : GetSortedGymsUseCase,
){

    suspend operator fun invoke():List<Gym>{
        gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }
}