package com.example.tournamentapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.R
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.database.match.SingleMatch
import com.example.tournamentapp.database.tournament.Tournament
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.darkGradient
import com.example.tournamentapp.ui.theme.goldColor
import com.example.tournamentapp.ui.theme.lightGradient
import com.example.tournamentapp.ui.theme.redColor
import com.example.tournamentapp.ui.theme.textColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TournamentResult(
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

    val results = viewModel.getSpecifMatches(tournamentId.toInt())
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

            StepsMTR(activeStep = 2, navController = navController, tournamentId = tournamentId)

            ListOfResults(
                results = results.reversed(),
                modifier = Modifier,
                viewModel = viewModel,
                tournamentId = tournamentId.toInt()
            )
            Spacer(modifier = Modifier.weight(1f))
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
                action = {
                    navigate(navController, Screen.WinnerView, arguments = listOf(tournamentId))
                },
            )
        }
    }
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfResults(
    results: List<SingleMatch>,
    modifier: Modifier,
    viewModel: TournamentViewModel,
    tournamentId: Int
) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 10.dp),
        modifier = Modifier
            .fillMaxHeight(.75f)
            .clip(RoundedCornerShape(10.dp))
            .background(brush = lightGradient)
            .padding(horizontal = 16.dp)
    ) {
        items(results.size) {

            if (results[it].isFinished) {

                var showDialog by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .padding(bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(.5f),
                        text = results[it].player1 + " " + results[it].player1Score
                                + " - " +
                                results[it].player2Score + " " + results[it].player2,
                        color = textColor,
                        fontSize = 16.sp
                    )

                    Box(
                        modifier = Modifier
                            .padding(vertical = 0.dp)
                            .clip(RoundedCornerShape(100))
                    ) {
                        IconButton(
                            onClick = { showDialog = true },
                            content = {
                                Icon(
                                    Icons.Filled.Refresh,
                                    contentDescription = "refresh match",
                                    tint = textColor
                                )
                            }
                        )
                    }
                }

                if(showDialog) {
                    AlertTournament(
                        onConfirm = {
                            viewModel.makeEditableScoreMatch(
                                results[it].matchId,
                                results[it].player1,
                                results[it].player2,
                                results[it].player1Score,
                                results[it].player2Score,
                                tournamentId = tournamentId)
                        },
                        onDismiss = { showDialog = false },
                        reset = true
                    )
                }

            }
        }

    }
}