package com.example.tournamentapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.database.points.PlayerStats
import com.example.tournamentapp.database.tournament.Tournament
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.darkGradient
import com.example.tournamentapp.ui.theme.lightBlueGradient
import com.example.tournamentapp.ui.theme.lightBlueGradientBG
import com.example.tournamentapp.ui.theme.lightGradient
import com.example.tournamentapp.ui.theme.textColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch

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


    val playerStats = viewModel.getPlayerStats(tournamentId.toInt())
        .collectAsState(emptyList()).value

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .background(darkGradient)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column() {
            ImageTrophy(navController = navController)

            if (tournament != null) {
                Text(
                    text = tournament.title,
                    fontSize = 22.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
            }

            StepsMTR(activeStep = 1, navController = navController, tournamentId = tournamentId)
            tablePlayerStats(
                playerStats = playerStats,
                modifier = Modifier
            )
            BottomMenu(
                tournamentOption = "END TOURNAMENT",
                deleteButton = true,
                deleteTournament = {
                    scope.launch {
                        if (tournament != null) {
                            viewModel.deleteTournament(tournament)
                        }
                    }
                    navigate(navController, Screen.MainScreen)
                },
                action = { navigate(navController, Screen.MainScreen) },
            )
        }
    }
}



@Composable
fun tablePlayerStats(
    playerStats: List<PlayerStats>,
    modifier: Modifier
) {

    Row(
        modifier = Modifier
            .padding(start = 20.dp, bottom = 10.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.width(100.dp)
        ) {
            Text(text = "Name", color = textColor)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "W", color = textColor)
            Text(text = "L", color = textColor)
            Text(text = "D", color = textColor)
            Text(text = "P", color = textColor, fontWeight = FontWeight.Bold)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(brush = lightGradient)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight(.85f)
    ) {
        items(playerStats.size) {
            var points = playerStats[it].won * 3 + playerStats[it].draws
            Row(
                modifier = Modifier
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(text = playerStats[it].playerName, color = textColor)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = playerStats[it].won.toString(), color = textColor)
                    Text(text = playerStats[it].lost.toString(), color = textColor)
                    Text(text = playerStats[it].draws.toString(), color = textColor)
                    Text(text = points.toString(), color = textColor, fontWeight = FontWeight.Bold,)
                }
            }
        }
    }
}