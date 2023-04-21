package com.example.tournamentapp.database

sealed interface TournamentEvent {
    object SaveTournament: TournamentEvent
    data class SetName(val name: String): TournamentEvent
    data class SetGameType(val gameType: String): TournamentEvent
    data class SetPlayers(val players: List<String>): TournamentEvent

    data class SetWinner(val winner: String): TournamentEvent
    data class SetIsFinished(val isFinished: Boolean): TournamentEvent

    data class SetDate(val date: String): TournamentEvent
}
