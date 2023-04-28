package com.example.tournamentapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.database.tournament.Tournament
import com.example.tournamentapp.ui.theme.darkGradient
import com.example.tournamentapp.ui.theme.textColor

@Composable
fun TournamentTable(
    tournamentId: String,
    navController: NavController,
    viewModel: TournamentViewModel
) {
    val tournament = viewModel.getSpecifTournament(tournamentId.toInt())
        .collectAsState(
            initial = Tournament(
                id = -1,
                title = "",
                players = "",
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
            ImageTrophy(navController = navController)

            Text(
                text = tournament!!.title,
                fontSize = 22.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )

            StepsMTR(activeStep = 1, navController = navController, tournamentId = tournamentId)
            Text("Table")
        }
    }
}