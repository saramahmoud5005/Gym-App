package com.example.gymapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.gymapp.ui.theme.GymAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymsAroundApp()
//            TestUI()
        }
    }
}

@Composable
private fun GymsAroundApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms"){
        composable(route = "gyms"){
            GymsScreen{ id ->
                navController.navigate("gyms/$id")
            }
        }
        composable(route = "gyms/{gym_id}",
            arguments = listOf(
                navArgument("gym_id"){
                    type = NavType.IntType
                },
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://www.gymsaround.com/details/{gym_id}"
                }
            )
        ){
//            val gymId = it.arguments?.getInt("gym_id")
            GymDetailsScreen()
        }
    }
}