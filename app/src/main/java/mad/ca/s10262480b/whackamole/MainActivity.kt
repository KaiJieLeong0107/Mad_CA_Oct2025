@file:OptIn(ExperimentalMaterial3Api::class)

package mad.ca.s10262480b.whackamole

import GameScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mad.ca.s10262480b.whackamole.basic.SettingsScreen
import mad.ca.s10262480b.whackamole.ui.theme.WhackAMoleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhackAMoleTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "game"){
                    composable("game") {GameScreen(navController)}
                    composable("settings"){ SettingsScreen(navController) }
                }
            }
        }
    }
}

