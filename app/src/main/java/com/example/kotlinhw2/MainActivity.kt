package com.example.kotlinhw2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Enum representing different screens
enum class Screen {
    SplashScreen,
    LoginRegisterScreen,
    RegisterScreen
}

// Main activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen by remember { mutableStateOf(Screen.SplashScreen) }

            when (currentScreen) {
                Screen.SplashScreen -> SplashScreen { currentScreen = Screen.LoginRegisterScreen }
                Screen.LoginRegisterScreen -> LoginRegisterScreen(
                    onLoginClick = { },
                    onRegisterClick = { currentScreen = Screen.RegisterScreen }
                )
                Screen.RegisterScreen -> RegisterScreen { currentScreen = Screen.LoginRegisterScreen }
            }
        }
    }
}

// Splash screen
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000) // 3-second delay
        onTimeout() // Navigate to the login/register
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //couldn't get this Image to read the stocksplash.webp from drawable, kept crashing the app
            //Image(painter = painterResource(id = R.drawable.stockSplash), contentDescription = null)
            Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)

            Text(text = "Declan's KotlinHw2 App", fontSize = 24.sp)
        }
    }
}
// Login/Register screen
@Composable
fun LoginRegisterScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onLoginClick) { Text("Login") }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRegisterClick) { Text("Register") }
    }
}
// Register screen
@Composable
fun RegisterScreen(onRegisterComplete: () -> Unit) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val dob = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = firstName.value, onValueChange = { firstName.value = it }, label = { Text("First Name") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = lastName.value, onValueChange = { lastName.value = it }, label = { Text("Last Name") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = dob.value, onValueChange = { dob.value = it }, label = { Text("Date of Birth") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = password.value, onValueChange = { password.value = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (validateInput(firstName.value, lastName.value, email.value, password.value)) {
                onRegisterComplete() // Navigate back after registration
            } else {
                // Handle validation error
            }
        }) {
            Text("Register")
        }
    }
}

// Input validation function
fun validateInput(firstName: String, lastName: String, email: String, password: String): Boolean {
    return firstName.length in 3..30 &&
            lastName.isNotEmpty() &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            password.isNotEmpty()
}
