package com.example.tournamentapp

import android.app.Application
import android.util.Log
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
import kotlin.math.absoluteValue

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
            val tournamentId = repository.insertTournament(tournament)

            Log.d("TOUR 1", tournamentId.toString())

            generateMatchesFFA(tournamentId = tournamentId, tournament = tournament)
        }
    }

    fun getSpecifTournament(tournamentId: Int): Flow<Tournament?> {
        return repository.getSpecifTournament(tournamentId)
    }

    fun getSpecifMatches(tournamentId: Int): Flow<List<SingleMatch>> {
        return singleMatchRepository.getAllMatches(tournamentId)
    }

    private suspend fun generateMatchesFFA(tournamentId: Long, tournament: Tournament) {

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
                    tournamentId = tournamentId
                ))
            }
        }
    }

    fun setScoreOfMatch(player1Score: String, player2Score: String, matchId: Int) {
        viewModelScope.launch {
            singleMatchRepository.updatePlayer1Score(player1Score,matchId)
            singleMatchRepository.updatePlayer2Score(player2Score,matchId)
            singleMatchRepository.matchIsFinished(true, matchId)
        }
    }

    fun makeEditableScoreMatch(matchId: Int) {
        viewModelScope.launch {
            singleMatchRepository.matchIsFinished(false, matchId)
        }
    }
}
