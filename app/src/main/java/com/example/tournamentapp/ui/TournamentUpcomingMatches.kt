package com.example.tournamentapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.database.Tournament
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.darkGradient


@Composable
fun TournamentUpcomingMatches(
    tournamentId: String,
    navController: NavController,
    viewModel: TournamentViewModel
) {
    val tournament = viewModel.getSpecifTournament(tournamentId.toInt())
        .collectAsState(
            initial = Tournament(
                id = -1,
                title = "",
                players = emptyList<String>().toString(),
                gameType = ""
            )
        ).value

    Box(
        modifier = Modifier
            .background(darkGradient)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column() {
            ImageTrophy()
            tournament?.let {
                testFunction(
                    tournament = tournament,
                )
            }

            Spacer(modifier = Modifier.fillMaxHeight(.85f))

            BottomMenu(tournamentOption = "END TOURNAMENT") {
                navigate(navController, Screen.MainScreen)
            }
        }
    }

}
@Composable
fun testFunction(
    tournament: Tournament,
) {
    Text(
        text = tournament.title,
        modifier = Modifier

    )
}
