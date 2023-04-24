package com.example.tournamentapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)





val lightBlueGradientBG = Color(0xFF0B1B4A)
val darkBlueGradientBG = Color(0xFF070A1D)

val lightBlueGradient = Color(0xFF6172DB)
val darkBlueGradient = Color(0xFF2D3A88)

val textColor = Color(0xFFEEEEEE)
val redColor = Color(0xFFFF2E00)
val goldColor = Color(0xFFE8A715)

val darkGradient = Brush.radialGradient(
    0.0f to lightBlueGradientBG,
    1.0f to darkBlueGradientBG,
    radius = 1500.0f,
    tileMode = TileMode.Repeated
)

val lightGradient = Brush.linearGradient(
    0.0f to lightBlueGradient,
    1.0f to darkBlueGradient,
    tileMode = TileMode.Repeated
)