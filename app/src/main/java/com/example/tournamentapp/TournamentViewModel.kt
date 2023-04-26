package com.example.tournamentapp

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tournamentapp.database.AppDatabase
import com.example.tournamentapp.database.match.SingleMatch
import com.example.tournamentapp.database.match.SingleMatchRepository
import com.example.tournamentapp.database.tournament.Tournament
import com.example.tournamentapp.database.tournament.TournamentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date

class TournamentViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TournamentsRepository
    private val singleMatchRepository: SingleMatchRepository




    init {
        val tournamentDatabase = AppDatabase.getDatabase(application).TournamentsDao()
        repository = TournamentsRepository(tournamentDatabase)

        val singleMatchDatabase = AppDatabase.getDatabase(application).SingleMatchDao()
        singleMatchRepository = SingleMatchRepository(singleMatchDatabase)

    }

    fun addTournament(tournament: Tournament) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTournament(tournament)
            generateMatchesFFA(tournament)
        }
    }

    fun getSpecifTournament(tournamentId: Int): Flow<Tournament?> {
        return repository.getSpecifTournament(tournamentId)
    }

    private suspend fun generateMatchesFFA(tournament: Tournament) {

        val listOfPlayers = tournament.players
            .trim('[', ']')
            .split(", ")
            .map {
                it.trim('"')
            }

        val size = listOfPlayers.size

        for (i in 0 until size) {
            for (j in i+1 until size) {
                singleMatchRepository.insertMatch(SingleMatch(
                    player1 = listOfPlayers[i],
                    player2 = listOfPlayers[j],
                    tournamentId = tournament.id
                ))
            }
        }
    }
}
