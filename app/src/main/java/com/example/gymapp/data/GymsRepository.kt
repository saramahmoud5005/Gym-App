package com.example.gymapp.data

import com.example.gymapp.Gym
import com.example.gymapp.GymApplication
import com.example.gymapp.GymFavouriteState
import com.example.gymapp.data.local.GymsDatabase
import com.example.gymapp.data.remote.GymsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsRepository {

    private val apiService: GymsApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://fcm-pushnotification-7a8f7-default-rtdb.firebaseio.com/")
        .build().create(GymsApiService::class.java)

    private var gymsDAO = GymsDatabase.getDaoInstance(GymApplication.getApplicationContext())

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