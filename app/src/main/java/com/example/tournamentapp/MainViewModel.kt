package com.example.tournamentapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tournamentapp.database.AppDatabase
import com.example.tournamentapp.database.Tournament
import com.example.tournamentapp.database.TournamentsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TournamentsRepository

    private val _tournaments = MutableStateFlow<List<Tournament>>(emptyList())
    val tournaments: MutableStateFlow<List<Tournament>> = _tournaments

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {

        val tournamentDatabase = AppDatabase.getDatabase(application).TournamentsDao()
        repository = TournamentsRepository(tournamentDatabase)

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
}