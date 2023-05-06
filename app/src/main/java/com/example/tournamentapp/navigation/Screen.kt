package com.example.tournamentapp.navigation

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object AddTournament: Screen("add_tournament_screen")
    object AddTournamentFinal: Screen("add_tournament_final_screen")
    object TournamentUpcomingMatches: Screen("tournament_upcoming_matches")
    object TournamentTable: Screen("tournament_table")
    object TournamentResult: Screen("tournament_result")
    object WinnerView: Screen("winner_view")
}
