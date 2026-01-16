import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.content.edit
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController){
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("game_prefs",Context.MODE_PRIVATE)


    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(30) }
    var moleIndex by remember { mutableStateOf(-1) }
    var isRunning by remember { mutableStateOf(false) }
    var highscore by remember { mutableStateOf(prefs.getInt("HIGH_SCORE",0)) }
    var showDialog by remember { mutableStateOf(false) }
    var molehit by remember { mutableStateOf(false) }
    LaunchedEffect(isRunning) {
        if(isRunning){
            launch {
                while (timeLeft>0){
                    delay(1000)
                    timeLeft--
                }
                isRunning=false
                if (score >highscore){
                    highscore = score
                    prefs.edit { putInt("HIGH_SCORE", highscore) }
                }
                showDialog = true
            }
        }
        launch {
            while (isRunning){
                delay((700..1000).random().toLong())
                moleIndex =(0..8).random()
                molehit = false
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text("Wack-a-Mole")

            },
            actions = {
                IconButton(onClick = {navController.navigate("settings")}) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )


        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Score: $score")
            Spacer(Modifier.width(8.dp))
            Text("Time: $timeLeft")
            Spacer(Modifier.width(8.dp))
            Text("High score: $highscore")

        }

        LazyVerticalGrid(

            GridCells.Fixed(3)

        ) {
            items(9) { index ->
                Box(
                    modifier = Modifier.padding(8.dp).size(80.dp).clickable {
                        if (isRunning && index == moleIndex && !molehit) {
                            score++
                            molehit =true
                        }
                    },
                    contentAlignment = Alignment.Center
                ) {
                    if (index == moleIndex) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Mole",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(50.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier.size(50.dp)
                                .background(Color.Gray, shape = CircleShape)
                        )
                    }
                }
            }

        }
        Button(
            onClick = {
                score = 0
                timeLeft = 30
                isRunning = true
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ){
            Text(if (isRunning)"Restart" else "Start")
        }
    }
    if(showDialog){
        AlertDialog(
            onDismissRequest = {showDialog=false},
            title = {Text("Game Over")},
            text = {Text("Final score: $score\nHigh score: $highscore")},
            confirmButton = {
                TextButton(onClick = {showDialog=false}) {
                    Text("OK")
                }
            },

        )
    }
}
