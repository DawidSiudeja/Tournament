package com.example.tournamentapp.database.match

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SingleMatchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMatch(singleMatch: SingleMatch)

    @Query("SELECT * FROM single_match_table WHERE tournamentId = :tournamentId")
    fun getAllMatches(tournamentId: Int): Flow<List<SingleMatch>>

    @Query("DELETE FROM single_match_table WHERE tournamentId = :tournamentId")
    suspend fun deleteAllMatchesFromTournament(tournamentId: Int)

}