package com.example.gymapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.gymapp.data.local.GymsDAO
import com.example.gymapp.data.local.GymsDatabase
import com.example.gymapp.data.remote.GymsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {

    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ) : GymsApiService {
        return retrofit.create(GymsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://fcm-pushnotification-7a8f7-default-rtdb.firebaseio.com/")
            .build()
    }
    @Provides
    fun provideRoomDao(
        db : GymsDatabase
    ) : GymsDAO {
        return db.dao
    }
    @Provides
    @Singleton
    fun provideRoomDataBase(
        @ApplicationContext context : Context
    ) : GymsDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            GymsDatabase::class.java,
            "gym_database",
        ).fallbackToDestructiveMigration()
            .build()
    }
}