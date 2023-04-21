package com.example.tournamentapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TournamentsDao {

    @Query("SELECT * FROM tournament_table")
    fun getAll(): List<Tournaments>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tournaments: Tournaments)

    @Delete
    suspend fun delete(tournaments: Tournaments)

    @Query("DELETE FROM tournament_table")
    suspend fun deleteAll()

}