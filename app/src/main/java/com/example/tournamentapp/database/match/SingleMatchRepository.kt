package com.example.tournamentapp.database.match

class SingleMatchRepository(private val singleMatchDao: SingleMatchDao) {

    suspend fun insertMatch(singleMatch: SingleMatch)
        = singleMatchDao.insertMatch(singleMatch)

    fun getAllMatches(tournamentId: Int)
        = singleMatchDao.getAllMatches(tournamentId)

    suspend fun deleteAllMatchesFromTournament(tournamentId: Int)
        = singleMatchDao.deleteAllMatchesFromTournament(tournamentId)

    suspend fun updatePlayer1Score(player1Score: String, matchId: Int)
        = singleMatchDao.updatePlayer1Score(player1Score, matchId)

    suspend fun updatePlayer2Score(player2Score: String, matchId: Int)
        = singleMatchDao.updatePlayer2Score(player2Score, matchId)

    suspend fun matchIsFinished(isFinished: Boolean, matchId: Int)
        = singleMatchDao.matchIsFinished(isFinished = isFinished, matchId = matchId)
}