package com.example.tournamentapp.database.points

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tournamentapp.database.match.SingleMatch
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerStatsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayerStats(playerStats: PlayerStats)

    @Query("UPDATE player_points_table SET won = won + 1 WHERE playerId = :playerId")
    suspend fun increaseWin(playerId: Int)

    @Query("UPDATE player_points_table SET lost = lost + 1 WHERE playerId = :playerId")
    suspend fun increaseLose(playerId: Int)

    @Query("UPDATE player_points_table SET draws = draws + 1 WHERE playerId = :playerId")
    suspend fun increaseDraw(playerId: Int)

    @Query("DELETE FROM player_points_table WHERE tournamentId = :tournamentId")
    suspend fun deleteAllPlayerStatsFromTournament(tournamentId: Int)

    @Query("SELECT * FROM player_points_table WHERE tournamentId = :tournamentId")
    fun getAllPlayerStats(tournamentId: Int): Flow<List<PlayerStats>>

}