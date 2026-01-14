package com.example.mobile.presentation.requests.student

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mobile.R
import com.example.mobile.presentation.Screen
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// --- Theme Colors ---
val IPCA_Green_Dark = Color(0xFF004E36)
val IPCA_Gold = Color(0xFF8D764F)
val Background_Light = Color(0xFFF8F9FA)
val Text_Black = Color(0xFF333333)
val Warning_Orange = Color(0xFFE65100)
val Alert_Red = Color(0xFFB00020)

// --- Models (Reusing the API structure) ---
// Ideally, these data classes should be in a shared 'domain/model' file
data class ApiRequestItem(
    val id: String,
    val productId: String,
    val productName: String,
    val qtyRequested: Int,
    val qtyDelivered: Int,
    val observation: String
)

data class ApiRequest(
    val id: String,
    val date: String,
    val status: String,
    val observation: String,
    val studentId: String,
    val items: List<ApiRequestItem>
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentMyRequestsScreen(
    onMenuClick: () -> Unit,
    navController: NavController,
    onCreateRequestClick: () -> Unit, // Navigate to Create Form
    onRequestDetailClick: (String) -> Unit // View details
) {
    // --- Mock Data (Student's own requests) ---
    val myRequests = listOf(
        ApiRequest(
            id = "req-001",
            date = "2026-01-08T18:21:31.087Z",
            status = "PENDENTE",
            observation = "Preciso de ajuda com bens essenciais para esta semana.",
            studentId = "me",
            items = listOf(
                ApiRequestItem("1", "p1", "Arroz Agulha 1kg", 2, 0, ""),
                ApiRequestItem("2", "p2", "Massa Esparguete", 2, 0, ""),
                ApiRequestItem("3", "p3", "Atum em Lata", 3, 0, "")
            )
        ),
        ApiRequest(
            id = "req-002",
            date = "2025-12-15T10:00:00.000Z",
            status = "ENTREGUE",
            observation = "Ran out of food.",
            studentId = "me",
            items = listOf(
                ApiRequestItem("4", "p4", "Leite Meio Gordo", 6, 6, "")
            )
        ),
        ApiRequest(
            id = "req-003",
            date = "2026-12-29T09:30:00.000Z",
            status = "CANCELADO",
            observation = "Pedido duplicado",
            studentId = "me",
            items = listOf(
                ApiRequestItem("5", "p1", "Arroz Agulha 1kg", 1, 0, "")
            )
        )
    )

    // --- State ---
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Ativos, 1 = Histórico

    // Filter Logic
    val filteredList = myRequests.filter { request ->
        if (selectedTab == 0) {
            request.status == "PENDENTE" || request.status == "EM_ANALISE"
        } else {
            request.status == "ENTREGUE" || request.status == "CANCELADO" || request.status == "REJEITADO"
        }
    }

    Scaffold(
        containerColor = Background_Light,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.CreateRequestScreen.route) },
                containerColor = IPCA_Gold,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Novo Pedido")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                // FIX: Only apply bottom padding so header stays at the top
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // 1. Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IPCA_Green_Dark)
                    .statusBarsPadding() // FIX: Handles status bar height internally
                    .padding(top = 16.dp, bottom = 0.dp, start = 20.dp, end = 20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onMenuClick) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        // If you have a Logo, put it here, otherwise just text
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logo_ipca),
                            contentDescription = "Logo",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Meus Pedidos",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Histórico de Apoio",
                                color = Color(0xFFA0C4B5),
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            // 2. Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = IPCA_Green_Dark,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = IPCA_Gold,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Ativos", fontWeight = if(selectedTab==0) FontWeight.Bold else FontWeight.Normal) },
                    selectedContentColor = IPCA_Gold,
                    unselectedContentColor = Color.Gray
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Histórico", fontWeight = if(selectedTab==1) FontWeight.Bold else FontWeight.Normal) },
                    selectedContentColor = IPCA_Gold,
                    unselectedContentColor = Color.Gray
                )
            }

            // 3. List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 80.dp), // Space for FAB
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredList.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(top = 60.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.History, null, tint = Color.LightGray, modifier = Modifier.size(60.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Sem registos nesta categoria.", color = Color.Gray)
                            }
                        }
                    }
                } else {
                    items(filteredList) { request ->
                        StudentRequestCard(
                            request = request,
                            onClick = { onRequestDetailClick(request.id) }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudentRequestCard(request: ApiRequest, onClick: () -> Unit) {

    // Date Formatting
    val formattedDate = remember(request.date) {
        try {
            val instant = Instant.parse(request.date)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy").withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            request.date
        }
    }

    // Items Summary logic
    val totalItems = request.items.sumOf { it.qtyRequested }
    val itemNames = request.items.take(2).joinToString(", ") { it.productName }
    val displayItems = if (request.items.size > 2) "$itemNames (+${request.items.size - 2})" else itemNames

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Status Icon Visual
            StatusIconBox(status = request.status)

            Spacer(modifier = Modifier.width(16.dp))

            // Center: Details
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Pedido #${request.id.takeLast(5)}", // Show short ID
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formattedDate,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = displayItems.ifEmpty { "Detalhes não disponíveis" },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Text_Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (request.observation.isNotEmpty()) {
                    Text(
                        text = request.observation,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Right: Chevron
            Icon(Icons.Default.ChevronRight, null, tint = IPCA_Gold)
        }
    }
}

@Composable
fun StatusIconBox(status: String) {
    val (color, icon) = when (status) {
        "PENDENTE" -> Pair(Warning_Orange, Icons.Default.WarningAmber)
        "EM_ANALISE" -> Pair(Color(0xFF1E88E5), Icons.Default.Info)
        "ENTREGUE" -> Pair(IPCA_Green_Dark, Icons.Default.CheckCircle)
        "CANCELADO" -> Pair(Alert_Red, Icons.Default.Block)
        else -> Pair(Color.Gray, Icons.Default.History)
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = status,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun StudentRequestsPreview() {
    StudentMyRequestsScreen(onMenuClick = {},navController = rememberNavController(), onCreateRequestClick = {}, onRequestDetailClick = {})
}