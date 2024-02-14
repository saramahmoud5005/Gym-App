package com.example.gymapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsRepository {

    private val apiService:GymsApiService = Retrofit.Builder()
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
     suspend fun updateLocalDatabase(){
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymsDAO.getFavouriteGyms()
        gymsDAO.addGyms(gyms)
        gymsDAO.updateGyms(
            favouriteGymsList.map{GymFavouriteState(id = it.id, true)}
        )
    }
     suspend fun getGymsInSpecificDispatchers() = withContext(Dispatchers.IO){
        try {
            updateLocalDatabase()
        }catch (e:Exception){
            if(gymsDAO.getGyms().isEmpty())
                throw Exception("something went wrong")
        }
        gymsDAO.getGyms()
    }
}