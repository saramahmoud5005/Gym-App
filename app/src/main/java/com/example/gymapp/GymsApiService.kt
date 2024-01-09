package com.example.gymapp

import retrofit2.Call
import retrofit2.http.GET

interface GymsApiService {
    @GET("gyms")
    fun getGyms():Call<List<Gym>>
}