package com.example.gymapp.data

import com.example.gymapp.Gym
import com.example.gymapp.GymApplication
import com.example.gymapp.GymFavouriteState
import com.example.gymapp.data.local.GymsDAO
import com.example.gymapp.data.local.GymsDatabase
import com.example.gymapp.data.remote.GymsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiService: GymsApiService,
    private val gymsDAO: GymsDAO,
){
     suspend fun toggleFavouriteGym(gymId:Int, currentFavouriteState:Boolean) = withContext(
        Dispatchers.IO){
        gymsDAO.updateGym(
            GymFavouriteState(
                id = gymId,
                isFavourite = currentFavouriteState
            )
        )
        gymsDAO.getGyms()
    }
     private suspend fun updateLocalDatabase(){
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymsDAO.getFavouriteGyms()
        gymsDAO.addGyms(gyms)
        gymsDAO.updateGyms(
            favouriteGymsList.map{ GymFavouriteState(id = it.id, true) }
        )
    }
     suspend fun loadGyms() = withContext(Dispatchers.IO){
        try {
            updateLocalDatabase()
        }catch (e:Exception){
            if(gymsDAO.getGyms().isEmpty())
                throw Exception("something went wrong")
        }
    }

    suspend fun getGyms():List<Gym>{
        return withContext(Dispatchers.IO){
            return@withContext gymsDAO.getGyms()
        }
    }
}