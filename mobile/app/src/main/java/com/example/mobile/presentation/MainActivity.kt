package com.example.mobile.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile.presentation.home.HomeView
import com.example.mobile.presentation.login.LoginView
import com.example.mobile.presentation.register.RegisterView
import com.example.mobile.presentation.requests.RequestDetailView
import com.example.mobile.presentation.requests.admin.RequestsView
import com.example.mobile.presentation.requests.student.CreateRequestView
import com.example.mobile.presentation.requests.student.StudentMyRequestsScreen
import com.example.mobile.presentation.students.StudentsView
import com.example.mobile.presentation.ui.theme.SASTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            SASTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.LoginScreen.route) {
                            LoginView(navController)
                        }
                        composable(route = Screen.RegisterScreen.route) {
                            RegisterView(navController)
                        }
                        composable(route = Screen.StudentsScreen.route) {
                            StudentsView(onMenuClick = {}, onAddStudent = {})
                        }
                        composable(route = Screen.HomeScreen.route) {
                            HomeView(navController)
                        }

                        // --- STUDENT REQUESTS FLOW ---
                        composable(route = Screen.StudentReqScreen.route) {
                            StudentMyRequestsScreen(
                                onMenuClick = {}, navController,
                                // Verify this route string matches exactly what is in your Screen class
                                onCreateRequestClick = {
                                    navController.navigate(Screen.CreateRequestScreen.route)
                                },
                                onRequestDetailClick = { requestId ->
                                    // navController.navigate("request_detail/$requestId")
                                }
                            )
                        }

                        composable(route = Screen.CreateRequestScreen.route) {
                            CreateRequestView(
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onSubmitRequest = { observation, items ->
                                    // Logic to save request goes here
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screen.RequestsScreen.route) {
                            RequestsView(onMenuClick = {}, onTicketClick = {})
                        }
                        composable(route = Screen.RequestDetailScreen.route) {
                            RequestDetailView(requestId = "83dc5ece-e885-4e66-8408-78e8018ad408", isAdmin = true, onNavigateBack = {
                                navController.popBackStack()
                            })
                        }
                    }
                }
            }
        }
    }
}

