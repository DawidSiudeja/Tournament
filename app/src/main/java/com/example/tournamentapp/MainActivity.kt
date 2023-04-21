package com.example.tournamentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.tournamentapp.navigation.Navigation
import com.example.tournamentapp.ui.theme.TournamentAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setVisible(viewModel.isLoading.value)
        }

        super.onCreate(savedInstanceState)
        setContent {
            TournamentAppTheme {
                Navigation()
            }
        }
    }
}

