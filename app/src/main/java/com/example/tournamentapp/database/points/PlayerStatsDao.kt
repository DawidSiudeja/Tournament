package com.example.tournamentapp.database.points

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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

    @Query("UPDATE player_points_table SET won = won - 1 WHERE playerId = :playerId")
    suspend fun decreaseWin(playerId: Int)

    @Query("UPDATE player_points_table SET lost = lost - 1 WHERE playerId = :playerId")
    suspend fun decreaseLose(playerId: Int)

    @Query("UPDATE player_points_table SET draws = draws - 1 WHERE playerId = :playerId")
    suspend fun decreaseDraw(playerId: Int)

    @Query("UPDATE player_points_table SET points = points + 1 WHERE playerId = :playerId")
    suspend fun increaseDrawPoints(playerId: Int)

    @Query("UPDATE player_points_table SET points = points + 3 WHERE playerId = :playerId")
    suspend fun increaseWinPoints(playerId: Int)

    @Query("UPDATE player_points_table SET points = points - 1 WHERE playerId = :playerId")
    suspend fun decreaseDrawPoints(playerId: Int)

    @Query("UPDATE player_points_table SET points = points - 3 WHERE playerId = :playerId")
    suspend fun decreaseWinPoints(playerId: Int)

    @Query("DELETE FROM player_points_table WHERE tournamentId = :tournamentId")
    fun deleteAllPlayerStatsFromTournament(tournamentId: Int)

    @Query("SELECT * FROM player_points_table WHERE tournamentId = :tournamentId ORDER BY points DESC")
    fun getAllPlayerStats(tournamentId: Int): Flow<List<PlayerStats>>

    @Query("SELECT * FROM player_points_table WHERE tournamentId = :tournamentId AND points = (SELECT MAX(points) FROM player_points_table)")
    fun getPlayerWithTheMostPoints(tournamentId: Int): Flow<List<PlayerStats>>

    @Query("SELECT * FROM player_points_table WHERE playerId = :playerId")
    fun getSpecifPlayerStats(playerId: Int): Flow<PlayerStats>

}