package com.example.tournamentapp.database.points

import kotlinx.coroutines.flow.Flow

class PlayerStatsRepository(private val playerStatsDao: PlayerStatsDao) {

    suspend fun insertPlayerStats(playerStats: PlayerStats)
        = playerStatsDao.insertPlayerStats(playerStats)

    suspend fun increaseWin(playerId: Int)
        = playerStatsDao.increaseWin(playerId)

    suspend fun increaseLose(playerId: Int)
        = playerStatsDao.increaseLose(playerId)

    suspend fun increaseDraw(playerId: Int)
        = playerStatsDao.increaseDraw(playerId)

    suspend fun decreaseWin(playerId: Int)
            = playerStatsDao.decreaseWin(playerId)

    suspend fun decreaseLose(playerId: Int)
            = playerStatsDao.decreaseLose(playerId)

    suspend fun decreaseDraw(playerId: Int)
            = playerStatsDao.decreaseDraw(playerId)

    fun deleteAllPlayerStatsFromTournament(tournamentId: Int)
        = playerStatsDao.deleteAllPlayerStatsFromTournament(tournamentId)

    suspend fun increaseDrawPoints(playerId: Int)
            = playerStatsDao.increaseDrawPoints(playerId)

    suspend fun increaseWinPoints(playerId: Int)
            = playerStatsDao.increaseWinPoints(playerId)

    suspend fun decreaseDrawPoints(playerId: Int)
            = playerStatsDao.decreaseDrawPoints(playerId)

    suspend fun decreaseWinPoints(playerId: Int)
            = playerStatsDao.decreaseWinPoints(playerId)

    fun getAllPlayerStats(tournamentId: Int)
        = playerStatsDao.getAllPlayerStats(tournamentId)

    fun getPlayerWithTheMostPoints(tournamentId: Int)
        = playerStatsDao.getPlayerWithTheMostPoints(tournamentId)

    fun getSpecifPlayerStats(playerId: Int): Flow<PlayerStats>
            = playerStatsDao.getSpecifPlayerStats(playerId)
}