package com.example.tournamentapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tournamentapp.R
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.navigate

@Composable
fun ImageTrophy(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painterResource(R.drawable.ic_trophy),
            contentDescription = "Trophy",
            modifier = Modifier
                .clickable {
                    navigate(navController = navController, destination = Screen.MainScreen)
                }
        )
    }
}