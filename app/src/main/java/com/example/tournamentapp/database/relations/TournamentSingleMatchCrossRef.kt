package com.example.tournamentapp.database.relations

import androidx.room.Entity

@Entity(primaryKeys = ["tournamentTitle", "matchId"])
data class TournamentSingleMatchCrossRef(
    val tournamentTitle: String,
    val matchId: String
)
