package com.example.communitywaterqualitymonitoring.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.communitywaterqualitymonitoring.R
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme
import com.example.communitywaterqualitymonitoring.ui.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    if (LocalInspectionMode.current) {
        MainContent(user = null, navController = navController, onSignOut = { })
        return
    }

    val authViewModel: AuthViewModel = viewModel()
    val user by authViewModel.user.collectAsState()
    
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen(onTimeout = { showSplash = false })
    } else {
        MainContent(
            user = user,
            navController = navController,
            onSignOut = { authViewModel.signOut() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    user: FirebaseUser?,
    navController: NavHostController,
    onSignOut: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (LocalInspectionMode.current) Color(0xFFE3F2FD) else Color.White)
    ) {
        // Centralized Background Image
        if (!LocalInspectionMode.current) {
            Image(
                painter = painterResource(id = R.drawable.app_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.4f // Increased visibility
            )
        }
        
        if (user == null) {
            NavHost(navController = navController, startDestination = "login") {
                composable("login") { 
                    LoginScreen(
                        onLoginSuccess = { /* Managed by user state */ },
                        onNavigateToSignUp = { navController.navigate("signup") }
                    ) 
                }
                composable("signup") { 
                    SignUpScreen(
                        onSignUpSuccess = { /* Managed by user state */ },
                        onNavigateToLogin = { navController.navigate("login") }
                    ) 
                }
            }
        } else {
            var selectedItem by remember { mutableIntStateOf(0) }
            val items = listOf("Dashboard", "Map", "Report", "History", "Wiki")
            val icons = listOf(
                Icons.Filled.Home,
                Icons.Filled.LocationOn,
                Icons.AutoMirrored.Filled.Send,
                Icons.AutoMirrored.Filled.List,
                Icons.Filled.Info
            )

            Scaffold(
                containerColor = Color.Transparent, // Ensure background shows through
                topBar = {
                    TopAppBar(
                        title = { Text("Sahyadri-Siri", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) },
                        actions = {
                            IconButton(onClick = onSignOut) {
                                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                },
                bottomBar = {
                    NavigationBar(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = { Icon(icons[index], contentDescription = item) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    navController.navigate(item.lowercase()) {
                                        popUpTo("dashboard") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "dashboard",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("dashboard") { DashboardScreen() }
                    composable("map") { MapScreen() }
                    composable("report") { ReportScreen() }
                    composable("history") { ReportHistoryScreen() }
                    composable("wiki") { WikiScreen() }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CommunityWaterQualityMonitoringTheme {
        MainContent(
            user = null,
            navController = rememberNavController(),
            onSignOut = {}
        )
    }
}
