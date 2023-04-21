package com.example.tournamentapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.R
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.goldColor
import com.example.tournamentapp.ui.theme.textColor



@Composable
fun ImageTrophy() {
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painterResource(R.drawable.ic_trophy),
            contentDescription = "Trophy"
        )
    }
}


fun navigate(navController: NavController, destination: Screen, arguments: List<String>? = null) {
    if (arguments == null) {
        navController.navigate(destination.route)
    } else {
        navController.navigate(destination.route+"/" + arguments[0] + "/" + arguments[1])
    }
}

@Composable
fun BottomMenu(
    tournamentOption: String,
    modifier: Modifier = Modifier,
    action: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(80.dp)
    ) {
        Button(
            onClick = { action() },
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = goldColor)
        ) {
            Text(
                text = tournamentOption,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

    }
}