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
    fun deleteAllMatchesFromTournament(tournamentId: Int)

    @Query("UPDATE single_match_table SET player1Score = :player1Score WHERE matchId = :matchId")
    suspend fun updatePlayer1Score(player1Score: String, matchId: Int)

    @Query("UPDATE single_match_table SET player2Score = :player2Score WHERE matchId = :matchId")
    suspend fun updatePlayer2Score(player2Score: String, matchId: Int)


    @Query("UPDATE single_match_table SET isFinished = :isFinished WHERE matchId = :matchId")
    suspend fun matchIsFinished(isFinished: Boolean, matchId: Int)
}