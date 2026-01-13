@file:OptIn(ExperimentalMaterial3Api::class)

package mad.ca.s10262480b.whackamole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mad.ca.s10262480b.whackamole.ui.theme.WhackAMoleTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhackAMoleTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Preview
@Composable
fun GameScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text("Wack-a-Mole")

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
        var score by remember { mutableStateOf(0) }
        var timeLeft by remember { mutableStateOf(30) }
        var moleIndex by remember { mutableStateOf(-1) }
        var isRunning by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Score: $score")
            Spacer(Modifier.width(8.dp))
            Text("Time: $timeLeft")
        }

        LazyVerticalGrid(

            GridCells.Fixed(3)

        ) {
            items(9) { index ->
                Box(
                    modifier = Modifier.padding(8.dp).size(80.dp).clickable {
                        if (isRunning && index == moleIndex) {
                            score++
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhackAMoleTheme {
        Greeting("Android")
    }
}