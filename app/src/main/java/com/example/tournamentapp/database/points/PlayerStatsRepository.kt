package com.example.tournamentapp.database.points

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

    fun getAllPlayerStats(tournamentId: Int)
        = playerStatsDao.getAllPlayerStats(tournamentId)

}