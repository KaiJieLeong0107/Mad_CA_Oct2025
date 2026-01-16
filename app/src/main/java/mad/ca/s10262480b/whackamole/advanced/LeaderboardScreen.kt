package mad.ca.s10262480b.whackamole.advanced

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(navController: NavController,scoreDao: ScoreDao,currentUser: UserEntity) {
    var leaderboard by remember { mutableStateOf(emptyList<LeaderboardEntry>()) }
    var personalBest by remember { mutableStateOf<Int?>(null) }
    var userRank by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(Unit) {
        val lb = scoreDao.getLeaderboard()
        val pb = scoreDao.getPersonalBest(currentUser.userId)
        val sorted = lb.sortedByDescending { it.bestScore }
        val rank =
            sorted.indexOfFirst { it.username == currentUser.username }.takeIf { it >= 0 }?.plus(
                1
            )

        leaderboard = sorted.take(10)
        personalBest = pb
        userRank = rank
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leaderboard") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp)) {
            Text("Leaderboard (Top 10)", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            if (leaderboard.isEmpty()) {
                Text("No scores yet")
            } else {
                leaderboard.fastForEachIndexed { index, entry ->
                    Text("${index + 1}. ${entry.username}  —  Score: ${entry.bestScore}", style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(Modifier.height(24.dp))
            Text("Your Personal Best: $personalBest", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text("Your Rank: ${userRank ?: "Unranked"}")
        }
    }
}
