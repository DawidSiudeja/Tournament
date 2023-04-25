package com.example.tournamentapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tournamentapp.database.AppDatabase
import com.example.tournamentapp.database.Tournament
import com.example.tournamentapp.database.TournamentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TournamentViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TournamentsRepository




    init {
        val tournamentDatabase = AppDatabase.getDatabase(application).TournamentsDao()
        repository = TournamentsRepository(tournamentDatabase)

    }

    fun addTournament(tournament: Tournament) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTournament(tournament)
        }
    }

    fun getSpecifTournament(tournamentId: Int): Flow<Tournament?> {
        return repository.getSpecifTournament(tournamentId)
    }

    fun generateMatches(tournament: Tournament): List<String> {

        var upcomingMatches: List<String> = emptyList()

        val listOfPlayers = tournament.players
            .trim('[', ']')
            .split(", ")
            .map {
                it.trim('"')
            }

        val size = listOfPlayers.size

        for (i in 0 until size) {
            for (j in i+1 until size) {
                val pair = "${listOfPlayers[i]} vs ${listOfPlayers[j]}"
                upcomingMatches += pair
            }
        }
        upcomingMatches = upcomingMatches.shuffled()

        return upcomingMatches
    }

}
