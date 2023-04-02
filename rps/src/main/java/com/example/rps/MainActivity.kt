package com.example.rps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rps.ui.theme.MultipleGameTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultipleGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Rockpaper(onExit = {})
                }
            }
        }
    }
}

fun scorer(faction: String, aaction: String): Int {
    // winner calculator 1 for player 0 for android 2 means a tie
    var win = 0
    if (faction == aaction)
        win = 2
    else if (faction == "Rock" && aaction == "Paper")
        win = 0
    else if (faction == "Rock" && aaction == "Scissor")
        win = 1
    else if (faction == "Paper" && aaction == "Rock")
        win = 1
    else if (faction == "Paper" && aaction == "Scissor")
        win = 0
    else if (faction == "Scissor" && aaction == "Paper")
        win = 1
    else if (faction == "Scissor" && aaction == "Rock")
        win = 0
    return win
}

fun genfora(): String {
    //Android choice generator
    val list = listOf("Rock", "Paper", "Scissor")
    val randomIndex = Random.nextInt(list.size)
    return list[randomIndex]
}

@Composable
fun Rockpbutton(bvlaue: String, onClick: () -> Unit) {
    // buttons generator
    Button(
        modifier = Modifier
            .height(108.dp)
            .width(108.dp)
            .padding(10.dp),
        onClick = onClick,
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(text = bvlaue)
    }
}

@Composable
fun Playeraction(playera: String, actionc: String) {
    //player and android choices display
    Text(
        text = playera,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
    Text(
        text = actionc,
        fontSize = 32.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Rockpaper(onExit: ()-> Unit) {
    //main compose
    val image = painterResource(id = R.drawable.rock)
    var faction by remember { mutableStateOf("Rock") }
    var aaction by remember { mutableStateOf("Paper") }
    var tscore by remember { mutableStateOf("0 / 0") }
    var pscore by remember { mutableStateOf(0) }
    var ascore by remember { mutableStateOf(0) }
    Column {
        //main column
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Score", fontSize = 30.sp, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )
        Text(
            text = tscore, fontSize = 50.sp, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        Row(modifier = Modifier.padding(top = 85.dp)) {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Playeraction(playera = "You Chose", actionc = faction)
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Playeraction(playera = "Android chose", actionc = aaction)
            }

        }
        Row(modifier = Modifier.padding(top = 70.dp)) {
            Column(
                Modifier
                    .fillMaxWidth(0.33f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Rockpbutton(bvlaue = "Rock") {
                    faction = "Rock"
                    aaction = genfora()
                    val win = scorer(faction, aaction)
                    if (win == 1)
                        pscore++
                    else if (win == 0)
                        ascore++
                    tscore = "$pscore / $ascore"
                }
            }
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Rockpbutton(bvlaue = "Paper") {
                    faction = "Paper"
                    aaction = genfora()
                    val win = scorer(faction, aaction)
                    if (win == 1)
                        pscore++
                    else if (win == 0)
                        ascore++
                    tscore = "$pscore / $ascore"
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Rockpbutton(bvlaue = "Scissor") {
                    faction = "Scissor"
                    aaction = genfora()
                    val win = scorer(faction, aaction)
                    if (win == 1)
                        pscore++
                    else if (win == 0)
                        ascore++
                    tscore = "$pscore / $ascore"
                }
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.weight(0.33f))
            if (pscore == 10 || ascore == 10) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (pscore == 10) "Player Win!" else "Android Win!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                pscore = 0
                                ascore = 0
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("New Game")
                        }
                        Button(
                            onClick = onExit,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = "Exit",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }




    }
}
