package com.example.tournamentapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.R
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.goldColor
import com.example.tournamentapp.ui.theme.lightBlueGradient
import com.example.tournamentapp.ui.theme.redColor
import com.example.tournamentapp.ui.theme.textColor


fun navigate(navController: NavController, destination: Screen, arguments: List<Any>? = null) {

    var destinationString = destination.route

    if (arguments != null) {
        for (element in arguments) {
            destinationString += "/$element"
        }
    }

    navController.navigate(destinationString)
}

@Composable
fun BottomMenu(
    tournamentOption: String,
    modifier: Modifier = Modifier,
    action: () -> Unit,
    deleteTournament: () -> Unit = {},
    deleteButton: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { action() },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
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

            if (deleteButton) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clip(RoundedCornerShape(100))
                        .background(redColor)
                ) {
                    IconButton(
                        onClick = { showDialog = true },
                        content = {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Usuń",
                                tint = textColor
                            )
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        AlertTournament(
            onConfirm = {
                deleteTournament()
            },
            onDismiss = { showDialog = false },
            delete = true
        )
    }
}


@Composable
fun AlertTournament(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    delete: Boolean = false,
    reset: Boolean = false,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            if(delete) {
                Text("Delete tournament", color = textColor)
            }
            if(reset) {
                Text("Reset match", color = textColor)
            }
        },
        text = {
            if (delete) {
                Text("Are you sure to delete this tournament?", color = textColor)
            }
            if (reset) {
                Text("Are you sure to reset this match?", color = textColor)
            }

       },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                if(delete) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "delete tournament button",
                        tint = redColor,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text("Delete", color = redColor, fontWeight = FontWeight.Bold)
                }
                if (reset) {
                    Text("Reset", color = redColor, fontWeight = FontWeight.Bold)
                }
            }

        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel", color = textColor)
            }
        },
        containerColor = lightBlueGradient
    )
}
