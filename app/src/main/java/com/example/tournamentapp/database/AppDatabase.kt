package com.example.tournamentapp.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tournamentapp.database.match.SingleMatch
import com.example.tournamentapp.database.match.SingleMatchDao
import com.example.tournamentapp.database.tournament.Tournament
import com.example.tournamentapp.database.tournament.TournamentsDao

@Database(entities = [Tournament::class, SingleMatch::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun TournamentsDao(): TournamentsDao
    abstract fun SingleMatchDao(): SingleMatchDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE

            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}

