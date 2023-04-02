package com.example.numberguessinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme
import java.util.*
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NumberGuessingGameScreen(onExit = { finish()})
                }
            }
        }
    }
}
var value = 0

@Composable
fun NumberGuessingGameScreen(onExit: () -> Unit) {

    //var numGuess by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val randNum = remember { mutableStateOf(Random.nextInt(1, 1000)) }
        var counterState = remember { mutableStateOf(0) }
        var text = remember { mutableStateOf("") }
        var message = remember { mutableStateOf("") }

        Text(
            text = stringResource(R.string.title_game),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(194.dp))

        TextField(
            value = text.value, onValueChange = {
                text.value = it
            },
        label = {
            Text(text = "Enter your guess")
            },
        placeholder = {
            Text(text = "your guess")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ))

        Spacer(Modifier.height(10.dp))
        Button(
            onClick = {
                val num = text.value.toIntOrNull()
                if (num != null) {
                    counterState.value++
                    if (num == randNum.value) {
                        message.value = "Correct! You guessed in ${counterState.value} tries."
                    } else if (num < randNum.value) {
                        message.value = "Too low!"
                    } else {
                        message.value = "Too high!"
                    }
                    text.value = ""
                }
            },
        )
        {
            Text("Enter")
        }
        Text(message.value)

        if (message.value.contains("Correct")) {
            Button(onClick = {
                randNum.value = Random.nextInt(1, 100)
                text.value = ""
                message.value = ""
                counterState.value = 0
            }) {
                Text("New Game")

            }
            Button(
                onClick = onExit
            ) {
                Text(text = "Exit",
                    fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NumberGuessingGameTheme {
        NumberGuessingGameScreen(onExit = {})
    }
}


