package com.example.tournamentapp.database.tournament

class TournamentsRepository(private val tournamentsDao: TournamentsDao) {

    suspend fun insertTournament(tournament: Tournament) =
        tournamentsDao.upsertTournament(tournament)

    fun deleteTournament(tournament: Tournament) =
        tournamentsDao.deleteTournament(tournament)

    suspend fun deleteAllTournaments() =
        tournamentsDao.deleteAllTournaments()

    fun getAllTournaments() =
        tournamentsDao.getAllTournaments()

    fun getSpecifTournament(tournamentId: Int) =
        tournamentsDao.getSpecifTournament(tournamentId)
}