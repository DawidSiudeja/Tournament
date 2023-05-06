package com.example.tournamentapp.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.tournamentapp.ui.theme.lightBlueGradient
import com.example.tournamentapp.ui.theme.lightGradient
import com.example.tournamentapp.ui.theme.redColor
import com.example.tournamentapp.ui.theme.textColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
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
                players = "",
                gameType = ""
            )
        ).value

    val upcomingMatches = viewModel.getSpecifMatches(tournamentId.toInt())
        .collectAsState(emptyList()).value

    val scope = rememberCoroutineScope()


    var winner = viewModel.getWinner(tournamentId.toInt())
        .collectAsState(emptyList()).value

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
            StepsMTR(0, navController, tournamentId)
            if (tournament != null) {
                ListOfMatches(
                    upcomingMatches = upcomingMatches,
                    modifier = Modifier,
                    viewModel = viewModel,
                    tournament = tournament
                )
            }
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

                    viewModel.getWinner(tournamentId.toInt())
                    if (winner.size == 1) {
                        navigate(navController, Screen.WinnerView, arguments = listOf(winner[0],tournamentId))
                    }
                    if (winner.size > 1) {
                        TODO()
                    }

                },

            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfMatches(
    upcomingMatches: List<SingleMatch>,
    modifier: Modifier,
    viewModel: TournamentViewModel,
    tournament: Tournament
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
        items(upcomingMatches.size) {

            var playersScore1 by remember { mutableStateOf(List(upcomingMatches.size) { "" }) }
            var playersScore2 by remember { mutableStateOf(List(upcomingMatches.size) { "" }) }

            if (!upcomingMatches[it].isFinished) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(.5f),
                        text = upcomingMatches[it].player1 + " vs " + upcomingMatches[it].player2,
                        color = textColor,
                        fontSize = 16.sp
                    )
                    TextField(
                        value = playersScore1[it],
                        onValueChange = {  newValue ->
                            playersScore1 = playersScore1.toMutableList().apply {
                                set(it, newValue)
                            }
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 12.sp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .width(50.dp)
                            .aspectRatio(1f)
                            .padding(end = 2.5.dp)
                            .border(
                                width = 2.dp,
                                color = textColor,
                                shape = RoundedCornerShape(10.dp)
                            ),

                        colors = TextFieldDefaults.textFieldColors(
                            textColor = textColor,
                            containerColor = Color.Transparent,
                        ),
                    )
                    TextField(
                        value = playersScore2[it],
                        onValueChange = {  newValue ->
                            playersScore2 = playersScore2.toMutableList().apply {
                                set(it, newValue)
                            }
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 12.sp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .width(50.dp)
                            .aspectRatio(1f)
                            .padding(start = 2.5.dp)
                            .border(
                                width = 2.dp,
                                color = textColor,
                                shape = RoundedCornerShape(10.dp)
                            )
                        ,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = textColor,
                            containerColor = Color.Transparent,
                        ),
                    )



                    Box(
                        modifier = Modifier
                            .padding(vertical = 0.dp)
                            .clip(RoundedCornerShape(100))
                    ) {
                        IconButton(
                            onClick = {
                                //if(playersScore1[it] == "" && playersScore2[it] == "") {
                                    viewModel.setScoreOfMatch(
                                        player1 = upcomingMatches[it].player1,
                                        player2 = upcomingMatches[it].player2,
                                        player1Score = playersScore1[it],
                                        player2Score = playersScore2[it],
                                        matchId = upcomingMatches[it].matchId,
                                        tournament = tournament
                                    )
                                //} else {
                                //    Toast.makeText(context, "Incorrect value", Toast.LENGTH_SHORT).show()
                                //}

                            },
                            content = {
                                Icon(
                                    painterResource(id = R.drawable.ic_done),
                                    contentDescription = "set score",
                                    tint = textColor
                                )
                            }
                        )
                    }


                }
            }
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
            fontWeight = if (activeStep == 2) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            fontSize = 14.sp
        )
    }
}