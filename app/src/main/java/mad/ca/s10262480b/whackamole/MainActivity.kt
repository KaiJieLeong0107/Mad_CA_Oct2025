@file:OptIn(ExperimentalMaterial3Api::class)

package mad.ca.s10262480b.whackamole

import GameScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import mad.ca.s10262480b.whackamole.advanced.AppDatabase
import mad.ca.s10262480b.whackamole.advanced.AuthScreen
import mad.ca.s10262480b.whackamole.advanced.LeaderboardScreen
import mad.ca.s10262480b.whackamole.advanced.UserEntity
import mad.ca.s10262480b.whackamole.basic.SettingsScreen
import mad.ca.s10262480b.whackamole.ui.theme.WhackAMoleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "whackmole_db"
        ).fallbackToDestructiveMigration().build()
        val userDao = db.userDao()
        val scoreDao = db.scoreDao()
        setContent {
            WhackAMoleTheme {
                val navController = rememberNavController()
                var currentUser by remember { mutableStateOf<UserEntity?>(null) }
                NavHost(navController = navController, startDestination = "auth"){
                    composable("auth"){AuthScreen(userDao){user: UserEntity ->
                        currentUser = user
                        navController.navigate("game/${user.userId}")
                    }}
                    composable("game/{userId}"){ backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toInt()?: 0
                        GameScreen(navController, scoreDao,userId)
                    }
                    composable("settings"){ SettingsScreen(navController) }
                    composable("leaderboard"){
                        currentUser?.let{
                            LeaderboardScreen(navController =navController,scoreDao=scoreDao,currentUser = it)
                        }
                    }

                }
            }
        }
    }
}

