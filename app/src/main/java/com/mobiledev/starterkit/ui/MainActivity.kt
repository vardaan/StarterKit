package com.mobiledev.starterkit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobiledev.starterkit.ui.theme.StarterKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "greeting") {
                composable("greeting") {
                    GreetingScreen("Vardan Sharma")
                }
            }
        }
    }
}


@Composable
fun GreetingScreen(name: String) {
    Scaffold { innerPadding ->
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StarterKitTheme {
        GreetingScreen("Android")
    }
}
