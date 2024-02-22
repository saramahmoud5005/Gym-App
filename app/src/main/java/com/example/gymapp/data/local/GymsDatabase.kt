package com.example.gymapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymapp.Gym

@Database(
    entities = [Gym::class],
    version = 1,
    exportSchema = false
)
abstract class GymsDatabase:RoomDatabase() {
    abstract val dao: GymsDAO

    companion object{

        private var daoInstance: GymsDAO? = null
        private fun buildDatabase(context: Context): GymsDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                GymsDatabase::class.java,
                "gym_database",
            ).fallbackToDestructiveMigration().build()
        }
        fun getDaoInstance(context: Context): GymsDAO {
            synchronized(this){
                if(daoInstance ==null){
                    daoInstance = buildDatabase(context).dao
                }
                return daoInstance as GymsDAO
            }
        }
    }
}