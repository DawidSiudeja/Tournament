package com.example.tournamentapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.ui.theme.darkGradient

@Composable
fun TournamentResult(
    tournamentId: String,
    navController: NavController,
    viewModel: TournamentViewModel
) {
    Box(
        modifier = Modifier
            .background(darkGradient)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column() {
            ImageTrophy()
            StepsMTR(activeStep = 2, navController = navController, tournamentId = tournamentId)
            Text("Result")
        }
    }
}