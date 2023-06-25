package com.example.tournamentapp.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tournamentapp.database.AppDatabase
import com.example.tournamentapp.database.match.SingleMatchRepository
import com.example.tournamentapp.database.points.PlayerStatsRepository
import com.example.tournamentapp.database.tournament.Tournament
import com.example.tournamentapp.database.tournament.TournamentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TournamentsRepository
    private val singleMatchRepository: SingleMatchRepository
    private val playerStatsRepository: PlayerStatsRepository



    private val _tournaments = MutableStateFlow<List<Tournament>>(emptyList())
    val tournaments: MutableStateFlow<List<Tournament>> = _tournaments

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {

        val tournamentDatabase = AppDatabase.getDatabase(application).TournamentsDao()
        repository = TournamentsRepository(tournamentDatabase)

        val singleMatchDatabase = AppDatabase.getDatabase(application).SingleMatchDao()
        singleMatchRepository = SingleMatchRepository(singleMatchDatabase)

        val playerStatsDatabase = AppDatabase.getDatabase(application).PlayerStatsDao()
        playerStatsRepository = PlayerStatsRepository(playerStatsDatabase)

        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false

        }

        viewModelScope.launch {
            repository.getAllTournaments().collect { tournaments ->
                _tournaments.value = tournaments
            }
        }

    }

    suspend fun deleteTournament(tournament: Tournament) = withContext(Dispatchers.IO) {
        repository.deleteTournament(tournament)
        singleMatchRepository.deleteAllMatchesFromTournament(tournament.id)
        playerStatsRepository.deleteAllPlayerStatsFromTournament(tournament.id)
    }

}