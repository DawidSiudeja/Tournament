package com.example.tournamentapp.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.*


@Composable
fun AddTournament(navController: NavController) {



    Box(
        modifier = Modifier
            .background(darkGradient)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column() {

            val context = LocalContext.current

            ImageTrophy(navController = navController)
            Text(
                text = "Add Tournament",
                fontSize = 22.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Steps(0)
            var basicInfoList = FormBasicInfo()
            Spacer(modifier = Modifier.weight(1f))

            BottomMenu(
                tournamentOption = "NEXT STEP",
                deleteButton = false,
                action = {
                    if(basicInfoList[0] != "") {
                        navigate(
                            navController = navController,
                            destination = Screen.AddTournamentFinal,
                            arguments = basicInfoList
                        )
                    } else {
                        Toast.makeText(context, "Enter the name of the tournament", Toast.LENGTH_SHORT).show()
                    }
                }
            )

        }
    }
}


@Composable
fun Steps(
    activeStep: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 45.dp)
    ) {
        Text(
            text = "Basic Info",
            color = textColor,
            modifier = Modifier
                .padding(end = 10.dp),
            fontWeight = if (activeStep == 0) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            fontSize = 14.sp
        )
        Text(
            text = "Players",
            color = textColor,
            modifier = Modifier
                .padding(end = 10.dp),
            fontWeight = if (activeStep == 1) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBasicInfo(): List<String> {
    val textStateName = remember {
        mutableStateOf("")
    }

    val radioOptions = listOf(
        "FFA",
        "Knockout Stage",
        "Group + Knockout Stage"
    )

    var selectedOption = remember {
        mutableStateOf(radioOptions[0])
    }

    TextField(
        value = textStateName.value,
        onValueChange = {
            textStateName.value = it
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = lightBlueGradient,
            textColor = textColor,
            unfocusedLabelColor = textColor,
            disabledLabelColor = textColor,
            focusedLabelColor = textColor,
            errorLabelColor = redColor,
        ),
        label = { Text("Enter tournament name") },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    )

    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                RadioButton(
                    selected = selectedOption.value == text,
                    onClick = { selectedOption.value = text },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = textColor,
                        unselectedColor = textColor,
                        disabledSelectedColor = textColor,
                        disabledUnselectedColor = textColor,
                    ),
                    enabled = text == "FFA",
                )
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 10.dp),
                    color = textColor,
                )
            }
        }
    }
    return listOf(textStateName.value, selectedOption.value)

}