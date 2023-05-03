package com.example.tournamentapp.database.tournament

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertTournament(tournament: Tournament): Long

    @Delete
    fun deleteTournament(tournament: Tournament)

    @Query("SELECT * FROM tournament_table")
    fun getAllTournaments(): Flow<List<Tournament>>

    @Query("SELECT * FROM tournament_table WHERE id = :tournamentId")
    fun getSpecifTournament(tournamentId: Int): Flow<Tournament>

    @Query("DELETE FROM tournament_table")
    suspend fun deleteAllTournaments()

}