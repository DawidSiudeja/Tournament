package com.example.tournamentapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.ui.AddTournament
import com.example.tournamentapp.ui.AddTournamentFinal
import com.example.tournamentapp.ui.TournamentResult
import com.example.tournamentapp.ui.TournamentTable
import com.example.tournamentapp.ui.TournamentUpcomingMatches
import com.example.tournamentapp.ui.WinnerView
import com.example.tournamentapp.ui.theme.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            HomeScreen(
                navController = navController,
                viewModel = viewModel()
            )
        }
        composable(route = Screen.AddTournament.route) {
            AddTournament(navController = navController)
        }
        composable(route = Screen.AddTournamentFinal.route +
                "/{tournamentName}/{tournamentType}",
            arguments = listOf(
                navArgument(
                    name = "tournamentName"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "tournamentType"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            ),
        ) { backStackEntry ->

            val tournamentName = requireNotNull(backStackEntry.arguments).getString("tournamentName").toString()
            val tournamentType = requireNotNull(backStackEntry.arguments).getString("tournamentType").toString()

            val basicInfoList = listOf(tournamentName, tournamentType)

            AddTournamentFinal(
                navController = navController,
                basicInfoList = basicInfoList,
                viewModel = viewModel()
            )
        }
        composable(route = Screen.TournamentUpcomingMatches.route +
                "/{tournamentId}",
            arguments = listOf(navArgument(
                name = "tournamentId"
            ) {
                type = NavType.StringType
                defaultValue = ""
            }),
            ) { backStackEntry ->

            val tournamentId = requireNotNull(backStackEntry.arguments).getString("tournamentId").toString()
            TournamentUpcomingMatches(
                tournamentId = tournamentId,
                navController = navController,
                viewModel = viewModel()
            )
        }

        composable(route = Screen.TournamentTable.route +
                "/{tournamentId}",
            arguments = listOf(navArgument(
                name = "tournamentId"
            ) {
                type = NavType.StringType
                defaultValue = ""
            }),
        ) { backStackEntry ->

            val tournamentId = requireNotNull(backStackEntry.arguments).getString("tournamentId").toString()
            TournamentTable(
                tournamentId = tournamentId,
                navController = navController,
                viewModel = viewModel()
            )
        }

        composable(route = Screen.TournamentResult.route +
                "/{tournamentId}",
            arguments = listOf(navArgument(
                name = "tournamentId"
            ) {
                type = NavType.StringType
                defaultValue = ""
            }),
        ) { backStackEntry ->

            val tournamentId = requireNotNull(backStackEntry.arguments).getString("tournamentId").toString()
            TournamentResult(
                tournamentId = tournamentId,
                navController = navController,
                viewModel = viewModel()
            )
        }

        composable(route = Screen.WinnerView.route +
                "/{playerId}/{tournamentId}",
            arguments = listOf(navArgument(
                name = "playerId"
            ){
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(
                name = "tournamentId"
            ) {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->

            val playerId = requireNotNull(backStackEntry.arguments).getString("playerId").toString()
            val tournamentId = requireNotNull(backStackEntry.arguments).getString("tournamentId").toString()

            WinnerView(
                navController = navController,
                playerId = playerId,
                tournamentId = tournamentId,
                viewModel = viewModel()
            )
        }
    }   
}

