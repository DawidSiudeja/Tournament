package com.example.tournamentapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.database.Tournament
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.darkGradient
import com.example.tournamentapp.ui.theme.textColor


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

    val upcomingMatches = listOf("Test vs Test2")


    Box(
        modifier = Modifier
            .background(darkGradient)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column() {
            ImageTrophy()

            StepsMTR(0, navController, tournamentId)

            ListOfMatches(
                upcomingMatches = upcomingMatches,
                modifier = Modifier
            )


            BottomMenu(tournamentOption = "END TOURNAMENT") {
                navigate(navController, Screen.MainScreen)
            }
        }
    }

}


@Composable
fun ListOfMatches(
    upcomingMatches: List<String>,
    modifier: Modifier,
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 10.dp),
        modifier = Modifier.fillMaxHeight(.85f)
    ) {
        items(upcomingMatches.size) {
            Text(
                modifier = Modifier
                    .padding(vertical = 15.dp),
                text = upcomingMatches[it],
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun StepsMTR (
    activeStep: Int,
    navController: NavController,
    tournamentId: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 45.dp)
    ) {
        Text(
            text = "Upcoming Matches",
            color = textColor,
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable {
                    navigate(
                        navController = navController,
                        destination = Screen.TournamentUpcomingMatches,
                        arguments = listOf(tournamentId)
                    )
                },
            fontWeight = if (activeStep == 0) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            fontSize = 14.sp
        )
        Text(
            text = "Table",
            color = textColor,
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable {
                    navigate(
                        navController = navController,
                        destination = Screen.TournamentTable,
                        arguments = listOf(tournamentId)
                    )
                },
            fontWeight = if (activeStep == 1) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            fontSize = 14.sp
        )
        Text(
            text = "Result",
            color = textColor,
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable {
                    navigate(
                        navController = navController,
                        destination = Screen.TournamentResult,
                        arguments = listOf(tournamentId)
                    )
                },
            fontWeight = if (activeStep == 1) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            fontSize = 14.sp
        )
    }
}