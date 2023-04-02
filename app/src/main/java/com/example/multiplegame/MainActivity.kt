package com.example.multiplegame

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.multiplegame.ui.theme.MultipleGameTheme
import com.example.numberguessinggame.NumberGuessingGameScreen
import com.example.quizgame.ui.GameViewModel
import com.example.quizgame.ui.QuizScreen
import com.example.rps.Rockpaper


sealed class Screen(val route: String){
    object Home: Screen("home")
    object NumberGuess: Screen("number guess game")
    object QuizGame: Screen("quiz game")
    object RPSGame: Screen("rock paper scissor game")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameViewModel: GameViewModel by viewModels()
        setContent {
            MultipleGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home" ){
                        composable(Screen.Home.route){
                            HomeScreen(navController = navController)
                        }
                        composable(Screen.NumberGuess.route){
                            NumberGuessScreen(navController = navController)
                        }
                        composable(Screen.QuizGame.route){
                            QuizGameScreen(navController = navController, gameViewModel = gameViewModel)
                        }
                        composable(Screen.RPSGame.route){
                            RPSGameScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Button(onClick = { navController.navigate(Screen.NumberGuess.route) },
            modifier = Modifier
                .size(350.dp,50.dp)
        ) {
            Text(
                text = "Number Guessing Game",
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(64.dp))
        Button(onClick = { navController.navigate(Screen.QuizGame.route) },
            modifier = Modifier
                .size(350.dp,50.dp)
        ) {
            Text(
                text = "Quiz Game",
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(64.dp))
        Button(onClick = { navController.navigate(Screen.RPSGame.route) },
            modifier = Modifier
                .size(350.dp,50.dp)
        ) {
            Text(
                text = "RPS Game",
                fontSize = 20.sp
            )
        }

    }
}

@Composable
fun NumberGuessScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)
    ) {
        NumberGuessingGameScreen(onExit = { navController.popBackStack() })
    }
}

@Composable
fun QuizGameScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)
    ) {
        QuizScreen(
            gameViewModel = gameViewModel,
            onPlayAgain = gameViewModel::resetQuestion,
            onExit = { navController.popBackStack() }
        )

    }
}

@Composable
fun RPSGameScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)
    ) {
        Rockpaper(
            onExit = {navController.popBackStack()}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MultipleGameTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController, modifier = Modifier)
    }
}