package com.example.tournamentapp.ui

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.darkGradient
import com.example.tournamentapp.ui.theme.textColor


import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.database.Tournament
import com.example.tournamentapp.ui.theme.goldColor
import com.example.tournamentapp.ui.theme.lightBlueGradient
import com.example.tournamentapp.ui.theme.redColor

@Composable
fun AddTournamentFinal(
    navController: NavController, basicInfoList: List<String>,
    viewModel: TournamentViewModel
) {
    Box(
        modifier = Modifier
            .background(darkGradient)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column() {
            ImageTrophy(navController = navController)
            Text(
                text = "Add Tournament",
                fontSize = 22.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Steps(1, navController)
            var players = FormPlayers(
                modifier = Modifier
                    .fillMaxHeight(1f)
            )
            BottomMenu(
                tournamentOption = "START TOURNAMENT",
                action = {
                    viewModel.addTournament(
                        Tournament(
                        title = basicInfoList[0],
                        gameType= basicInfoList[1],
                        players = players.toString())
                    )
                    navigate(navController, Screen.MainScreen)
                }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPlayers(
    modifier: Modifier
): List<String> {
    var players by remember { mutableStateOf(listOf("")) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 10.dp),
        modifier = Modifier.fillMaxHeight(.83f),
    ) {
        itemsIndexed(players) { index, player ->
            TextField(
                value = player,
                onValueChange = { newValue ->
                    players = players.toMutableList().apply {
                        set(index, newValue)
                    }
                },
                label = { Text("Player ${index + 1}") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(lightBlueGradient),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = lightBlueGradient,
                    textColor = textColor,
                    unfocusedLabelColor = textColor,
                    disabledLabelColor = textColor,
                    focusedLabelColor = textColor,
                    errorLabelColor = redColor,
                ),
            )

            if (index == players.lastIndex) {
                Button(
                    onClick = { players = players + "" },
                    modifier = Modifier

                        .padding(top = 80.dp)
                        .height(45.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = goldColor
                    )
                ) {
                    Text(
                        text = "Add Player",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

    return players
}