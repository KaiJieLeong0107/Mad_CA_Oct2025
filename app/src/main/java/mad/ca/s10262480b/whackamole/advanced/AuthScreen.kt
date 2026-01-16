package mad.ca.s10262480b.whackamole.advanced

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest

fun hashPassword(password: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
    return bytes.joinToString (""){"%02x".format(it)}
}
@Composable
fun AuthScreen(
    userDao: UserDao,
    onLoginSuccess: (UserEntity)-> Unit
){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign In / Sign Up", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        TextField(value = username, onValueChange = {username = it}, label = {Text("Username")})
        Spacer(Modifier.height(16.dp))

        TextField(value = password, onValueChange = {password = it}, label = {Text("Password")}, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(8.dp))
        Row {
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val hashedPassword = hashPassword(password)
                    val user = userDao.login(username, hashedPassword)
                    if (user != null) {
                        withContext(Dispatchers.Main){
                            onLoginSuccess(user)
                        }
                    } else {
                        withContext(Dispatchers.Main){
                            message = "Invalid credentials"
                        }
                    }
                }

            }) {Text("Sign In")}

            Spacer(Modifier.height(8.dp))
            Spacer(Modifier.padding(8.dp))

            Button(onClick = {
                if(username.isBlank()||password.isBlank()){
                    message = "Username and password cannot be empty"
                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        val existing = userDao.getUser(username)
                        if (existing != null) {
                            withContext(Dispatchers.Main) {
                                message = "Username already exists"
                            }
                        } else {
                            val hashedPassword = hashPassword(password)
                            userDao.insertUser(
                                UserEntity(
                                    username = username,
                                    password = hashedPassword
                                )
                            )
                            withContext(Dispatchers.Main) {
                                message = "User created!"
                            }
                        }
                    }
                }
            }){Text("Sign Up")}

        }
        Spacer(Modifier.height(16.dp))
        Text(message)
    }
}
