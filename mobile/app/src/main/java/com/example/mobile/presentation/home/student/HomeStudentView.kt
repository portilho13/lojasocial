package com.example.mobile.presentation.home.student

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mobile.R
import com.example.mobile.domain.models.RequestStatus
import com.example.mobile.domain.models.SupportRequest
import com.example.mobile.presentation.Screen
import com.example.mobile.presentation.home.admin.components.LogoutConfirmationDialog
import com.example.mobile.presentation.requests.student.StudentMyRequestsScreen
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Warning_Orange
import com.example.mobile.presentation.components.NavigationDrawerStudent
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeStudentView(navController: NavController,
                    viewModel: HomeStudentViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("dashboard") }
    var showLogoutDialog by remember { mutableStateOf(false) }

    // Navegação após logout
    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(0) { inclusive = true } // Limpa toda a backstack
            }
            viewModel.resetLogoutState()
        }
    }

    // Mostrar erros
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                actionLabel = "OK"
            )
            viewModel.onEvent(HomeEvent.ClearError)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerStudent(
                currentScreen = currentScreen,
                onItemSelected = { id ->
                    currentScreen = id
                    scope.launch { drawerState.close() }
                },
                onLogout = {
                    showLogoutDialog = true
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        // Switch content based on ID
        when (currentScreen) {

            "dashboard" -> DashboardStudent(
                requests = state.requests,
                onMenuClick = { scope.launch { drawerState.open() } }
            )

            "studentsreq" -> StudentMyRequestsScreen(
                onMenuClick = { scope.launch { drawerState.open() } },navController,
                onCreateRequestClick = {}, onRequestDetailClick = {}
            )


        }
    }
    // Dialog de confirmação de logout
    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                viewModel.onEvent(HomeEvent.Logout)
            },
            onDismiss = {
                showLogoutDialog = false
            },
            isLoading = state.isLoading
        )
    }

    // Snackbar para erros
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
            //.align(Alignment.BottomCenter)
            .padding(16.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardStudent(
    requests: List<SupportRequest>,
    onMenuClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background_Light)
            .verticalScroll(scrollState)
    ) {
        // 1. Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(IPCA_Green_Dark)
                .padding(top = 24.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo_ipca),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Os Meus Pedidos",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Histórico e Estado",
                        color = Color(0xFFA0C4B5), // Light Green text
                        fontSize = 12.sp
                    )
                }
            }
        }

        // 2. Requests List
        Column(modifier = Modifier.padding(20.dp)) {
            if (requests.isEmpty()) {
                Text(
                    text = "Ainda não tem pedidos registados.",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )
            } else {
                requests.forEach { request ->
                    RequestItemCard(request)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(40.dp)) // Bottom spacing
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestItemCard(request: SupportRequest) {
    val statusColor = when (request.status) {
        RequestStatus.PENDENTE -> Warning_Orange
        RequestStatus.APROVADO -> IPCA_Green_Dark
        RequestStatus.ENTREGUE -> IPCA_Gold
        RequestStatus.CANCELADO -> Color.Gray
    }

    val formattedDate = try {
        val parsedDate = LocalDateTime.parse(request.date, DateTimeFormatter.ISO_DATE_TIME)
        parsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
    } catch (e: Exception) {
        request.date
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedDate,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = request.status.name,
                    color = statusColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (!request.observation.isNullOrBlank()) {
                Text(
                    text = request.observation,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (request.items.isNotEmpty()) {
                Text(
                    text = "Itens:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = IPCA_Green_Dark
                )
                Spacer(modifier = Modifier.height(4.dp))
                request.items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "- ${item.productName}",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "x${item.qtyRequested}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeStudentPreview() {
    HomeStudentView(navController = rememberNavController())
}
