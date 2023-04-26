package com.example.tournamentapp.database.match

class SingleMatchRepository(private val singleMatchDao: SingleMatchDao) {

    suspend fun insertMatch(singleMatch: SingleMatch)
        = singleMatchDao.insertMatch(singleMatch)

    fun getAllMatches(tournamentId: Int)
        = singleMatchDao.getAllMatches(tournamentId)

    suspend fun deleteAllMatchesFromTournament(tournamentId: Int)
            = singleMatchDao.deleteAllMatchesFromTournament(tournamentId)

}