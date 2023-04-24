package com.example.tournamentapp

import android.app.Application
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

    private val _tournaments = MutableStateFlow<List<Tournament>>(emptyList())
    val tournaments: MutableStateFlow<List<Tournament>> = _tournaments



    init {
        val tournamentDatabase = AppDatabase.getDatabase(application).TournamentsDao()
        repository = TournamentsRepository(tournamentDatabase)

        viewModelScope.launch {
            repository.getAllTournaments().collect { tournaments ->
                _tournaments.value = tournaments
            }
        }

    }

    fun addTournament(tournament: Tournament) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTournament(tournament)
        }
    }

    fun getSpecifTournament(tournamentId: Int): Flow<Tournament?> {
        return repository.getSpecifTournament(tournamentId)
    }

}
