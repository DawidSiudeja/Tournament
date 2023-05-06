package com.example.tournamentapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tournamentapp.database.AppDatabase
import com.example.tournamentapp.database.match.SingleMatch
import com.example.tournamentapp.database.match.SingleMatchRepository
import com.example.tournamentapp.database.points.PlayerStats
import com.example.tournamentapp.database.points.PlayerStatsRepository
import com.example.tournamentapp.database.tournament.Tournament
import com.example.tournamentapp.database.tournament.TournamentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.math.absoluteValue

class TournamentViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TournamentsRepository
    private val singleMatchRepository: SingleMatchRepository
    private val playerStatsRepository: PlayerStatsRepository

    private val _playerList = MutableStateFlow<List<PlayerStats>>(emptyList())
    val playerList: MutableStateFlow<List<PlayerStats>> = _playerList

    init {

        val tournamentDatabase = AppDatabase.getDatabase(application).TournamentsDao()
        repository = TournamentsRepository(tournamentDatabase)

        val singleMatchDatabase = AppDatabase.getDatabase(application).SingleMatchDao()
        singleMatchRepository = SingleMatchRepository(singleMatchDatabase)

        val playerStatsDatabase = AppDatabase.getDatabase(application).PlayerStatsDao()
        playerStatsRepository = PlayerStatsRepository(playerStatsDatabase)


    }

    fun addTournament(tournament: Tournament) {
        viewModelScope.launch(Dispatchers.IO) {
            val tournamentId = repository.insertTournament(tournament)

            Log.d("TOUR 1", tournamentId.toString())

            addingPlayersForTournament(tournamentId = tournamentId, tournament = tournament)
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

    private suspend fun addingPlayersForTournament(tournamentId: Long, tournament: Tournament) {

        val listOfPlayers = tournament.players
            .trim('[', ']')
            .split(", ")
            .map {
                it.trim('"')
            }
        for (i in listOfPlayers.indices) {
            playerStatsRepository.insertPlayerStats(PlayerStats(
                playerName = listOfPlayers[i],
                tournamentId = tournamentId
            ))
        }
    }

    fun setScoreOfMatch(player1: String,
                        player2: String,
                        player1Score: String,
                        player2Score: String,
                        matchId: Int,
                        tournament: Tournament,
    ) {
        viewModelScope.launch {
            singleMatchRepository.updatePlayer1Score(player1Score,matchId)
            singleMatchRepository.updatePlayer2Score(player2Score,matchId)
            singleMatchRepository.matchIsFinished(true, matchId)

            val playerList = playerStatsRepository.getAllPlayerStats(tournament.id).first()


            var player1Score = player1Score.toInt()
            var player2Score = player2Score.toInt()




            // Draw
            if (player1Score == player2Score) {

                for(player in playerList) {
                    Log.d("DRAW PLAYER", player.playerName + " " + player1)
                    Log.d("DRAW PLAYER", player.playerName + " " + player2)
                    if (player.playerName == player1) {
                        playerStatsRepository.increaseDraw(player.playerId)
                        playerStatsRepository.increaseDrawPoints(player.playerId)
                    }
                    if (player.playerName == player2) {
                        playerStatsRepository.increaseDraw(player.playerId)
                        playerStatsRepository.increaseDrawPoints(player.playerId)
                    }
                }
            }

            // WIN PLAYER 1
            if(player1Score > player2Score) {
                for (player in playerList) {
                    if (player.playerName == player1) {
                        playerStatsRepository.increaseWin(player.playerId)
                        playerStatsRepository.increaseWinPoints(player.playerId)
                    }
                    if (player.playerName == player2) {
                        playerStatsRepository.increaseLose(player.playerId)
                    }
                }
            }

            // WIN PLAYER 2
             if(player2Score > player1Score) {
                 for (player in playerList) {
                     if (player.playerName == player2) {
                         playerStatsRepository.increaseWin(player.playerId)
                         playerStatsRepository.increaseWinPoints(player.playerId)
                     }
                     if (player.playerName == player1) {
                         playerStatsRepository.increaseLose(player.playerId)
                     }
                 }
             }



        }
    }



    fun makeEditableScoreMatch(matchId: Int,
                               player1: String,
                               player2: String,
                               player1Score: Int,
                               player2Score: Int,
                               tournamentId: Int) {
        viewModelScope.launch {
            singleMatchRepository.matchIsFinished(false, matchId)

            val playerList = playerStatsRepository.getAllPlayerStats(tournamentId).first()

            // Draw
            if (player1Score == player2Score) {

                for(player in playerList) {
                    if (player.playerName == player1) {
                        playerStatsRepository.decreaseDraw(player.playerId)
                        playerStatsRepository.decreaseDrawPoints(player.playerId)
                    }
                    if (player.playerName == player2) {
                        playerStatsRepository.decreaseDraw(player.playerId)
                        playerStatsRepository.decreaseDrawPoints(player.playerId)
                    }
                }
            }

            // WIN PLAYER 1
            if(player1Score > player2Score) {
                for (player in playerList) {
                    if (player.playerName == player1) {
                        playerStatsRepository.decreaseWin(player.playerId)
                        playerStatsRepository.decreaseWinPoints(player.playerId)
                    }
                    if (player.playerName == player2) {
                        playerStatsRepository.decreaseLose(player.playerId)
                    }
                }
            }

            // WIN PLAYER 2
            if(player2Score > player1Score) {
                for (player in playerList) {
                    if (player.playerName == player2) {
                        playerStatsRepository.decreaseWin(player.playerId)
                        playerStatsRepository.decreaseWinPoints(player.playerId)
                    }
                    if (player.playerName == player1) {
                        playerStatsRepository.decreaseLose(player.playerId)
                    }
                }
            }
        }
    }

    fun getPlayerStats(tournamentId: Int): Flow<List<PlayerStats>> {
        return playerStatsRepository.getAllPlayerStats(tournamentId)
    }

    suspend fun deleteTournament(tournament: Tournament) = withContext(Dispatchers.IO) {
        repository.deleteTournament(tournament)
        singleMatchRepository.deleteAllMatchesFromTournament(tournament.id)
        playerStatsRepository.deleteAllPlayerStatsFromTournament(tournament.id)
    }

    fun setWinner(tournamentId: Int) {
        TODO()
    }

}
