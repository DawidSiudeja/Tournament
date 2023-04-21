package com.example.tournamentapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tournamentapp.ui.AddTournament
import com.example.tournamentapp.ui.AddTournamentFinal
import com.example.tournamentapp.ui.theme.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            HomeScreen(navController = navController)
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
            )
        }
    }   
}

