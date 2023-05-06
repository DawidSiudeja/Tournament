package com.example.tournamentapp.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.tournamentapp.R
import com.example.tournamentapp.TournamentViewModel
import com.example.tournamentapp.database.points.PlayerStats
import com.example.tournamentapp.navigation.Screen
import com.example.tournamentapp.ui.theme.darkGradient
import com.example.tournamentapp.ui.theme.textColor
import kotlinx.coroutines.launch

@Composable
fun WinnerView(
    navController: NavController,
    playerId: String,
    viewModel: TournamentViewModel,
    tournamentId: String
) {




    val scope = rememberCoroutineScope()

    val winner = viewModel.getSpecifyPlayer(playerId.toInt()).collectAsState(
        initial = PlayerStats(
            playerName = "",
            tournamentId = -1
        )).value

    Box(
        modifier = Modifier
            .background(darkGradient)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column {
            ImageTrophy(navController = navController)

            Text(
                text = "Congratulations " + winner.playerName,
                fontSize = 22.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))

            GifImage()

            Spacer(modifier = Modifier
                .fillMaxHeight(.55f)
            )

            BottomMenu(
                tournamentOption = "HOME",
                deleteButton = false,
                action = {
                    scope.launch {
                        viewModel.setWinner(winner, tournamentId.toInt())
                    }

                    navigate(navController, Screen.MainScreen)
                },
            )



        }
    }

}





@Composable
fun GifImage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.winner).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    )
}