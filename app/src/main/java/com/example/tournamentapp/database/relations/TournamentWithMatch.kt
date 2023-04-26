package com.example.tournamentapp.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tournamentapp.database.match.SingleMatch
import com.example.tournamentapp.database.tournament.Tournament

data class TournamentWithSingleMatch(
    @Embedded val tournament: Tournament,
    @Relation(
        parentColumn = "tournamentTitle",
        entityColumn = "matchId",
        associateBy = Junction(TournamentSingleMatchCrossRef::class)
    )
    val matches: List<SingleMatch>
)
