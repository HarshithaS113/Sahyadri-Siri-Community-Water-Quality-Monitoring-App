package com.example.communitywaterqualitymonitoring.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.communitywaterqualitymonitoring.R
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme
import com.example.communitywaterqualitymonitoring.ui.viewmodels.AuthViewModel

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToSignUp: () -> Unit) {
    if (LocalInspectionMode.current) {
        LoginContent(
            loading = false,
            error = null,
            onLoginClick = { _, _ -> },
            onNavigateToSignUp = onNavigateToSignUp
        )
        return
    }

    val viewModel: AuthViewModel = viewModel()
    val user by viewModel.user.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(user) {
        if (user != null) {
            onLoginSuccess()
        }
    }

    LoginContent(
        loading = loading,
        error = error,
        onLoginClick = { email, pass -> viewModel.signIn(email, pass) },
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@Composable
fun LoginContent(
    loading: Boolean,
    error: String?,
    onLoginClick: (String, String) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!LocalInspectionMode.current) {
            Image(
                painter = painterResource(id = R.drawable.app_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.3f
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Sahyadri-Siri Login", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            )

            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onLoginClick(email, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login")
                }
            }

            TextButton(onClick = onNavigateToSignUp, enabled = !loading) {
                Text("Don't have an account? Sign Up")
            }
        }
    }
}

@Preview(showBackground = true, name = "Login: Normal")
@Composable
fun LoginPreview() {
    CommunityWaterQualityMonitoringTheme {
        LoginContent(loading = false, error = null, onLoginClick = { _, _ -> }, onNavigateToSignUp = {})
    }
}

@Preview(showBackground = true, name = "Login: Loading")
@Composable
fun LoginLoadingPreview() {
    CommunityWaterQualityMonitoringTheme {
        LoginContent(loading = true, error = null, onLoginClick = { _, _ -> }, onNavigateToSignUp = {})
    }
}

@Preview(showBackground = true, name = "Login: Error")
@Composable
fun LoginErrorPreview() {
    CommunityWaterQualityMonitoringTheme {
        LoginContent(loading = false, error = "Invalid email or password", onLoginClick = { _, _ -> }, onNavigateToSignUp = {})
    }
}
