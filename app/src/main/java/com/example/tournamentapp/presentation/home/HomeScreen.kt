package com.example.tournamentapp.ui.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.presentation.home.HomeViewModel
import com.example.tournamentapp.database.tournament.Tournament
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.presentation.components.ImageTrophy
import com.example.tournamentapp.ui.AlertTournament
import com.example.tournamentapp.ui.BottomMenu
import com.example.tournamentapp.ui.navigate
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {

    val tournaments =  viewModel.tournaments.collectAsState().value.reversed()


    Box(
        modifier = Modifier
            .background(darkGradient)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ImageTrophy(navController = navController)
            LatestTournaments(
                lightGradient = lightGradient,
                tournaments = tournaments,
                navController = navController,
                viewModel = viewModel,
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomMenu(
                tournamentOption = "NEW TOURNAMENT",
                deleteButton = false,
                action = { navigate(navController,Screen.AddTournament) },
                modifier = Modifier
            )
        }
    }

}


@Composable
fun LatestTournaments(
    tournaments: List<Tournament>,
    lightGradient: Brush,
    navController: NavController,
    viewModel: HomeViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column() {
            Text(
                "Latest",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = textColor,
                style = MaterialTheme.typography.headlineMedium,
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 10.dp),
                modifier = Modifier.fillMaxHeight(.8f),
            ) {
                items(tournaments.size) {
                    LatestTournamentsItem(
                        tournaments = tournaments[it],
                        lightGradient,
                        navController,
                        viewModel,
                    )
                }
            }

        }
    }
}

@Composable
fun LatestTournamentsItem(
    tournaments: Tournament,
    lightGradient: Brush,
    navController: NavController,
    viewModel: HomeViewModel,
    ) {

    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(lightGradient)
            .padding(12.dp)
            .clickable {
                if (!tournaments.isFinished) {
                    navigate(
                        navController = navController,
                        destination = Screen.TournamentUpcomingMatches,
                        arguments = listOf(tournaments.id)
                    )
                } else {
                    showDialog = true
                }

            },
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
        ) {
            Column() {
                Text(
                    tournaments.title,
                    color = textColor,
                )
                Text(
                    tournaments.date.substring(0,10),
                    color = textColor,
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            Column() {
                Text(
                    "Winner",
                    color = textColor,
                )
                Text(
                    tournaments.winner,
                    color = textColor,
                )
            }

        }

        if(showDialog) {
            AlertTournament(
                onConfirm = {
                    scope.launch {
                        viewModel.deleteTournament(tournaments)
                    }
                    showDialog = false
                },
                onDismiss = { showDialog = false },
                delete = true
            )
        }

    }
}



