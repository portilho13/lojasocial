package com.example.mobile.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import com.example.mobile.R
import com.example.mobile.presentation.components.NavigationDrawer
import com.example.mobile.presentation.home.components.DashboardCard
import com.example.mobile.presentation.home.components.ExpiringItemRow
import com.example.mobile.presentation.product.ProductView
import com.example.mobile.presentation.product.types.ProductTypeView
import com.example.mobile.presentation.requests.admin.RequestsView
import com.example.mobile.presentation.stock.StockView
import com.example.mobile.presentation.students.StudentsView
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Warning_Orange
import kotlinx.coroutines.launch

// --- Mock Data Models (Matches your API structure) ---
data class StockSummary(
    val totalItems: Int,
    val lowStockCount: Int,
    val expiringSoonCount: Int
)

data class ExpiringItem(
    val productName: String,
    val location: String,
    val quantity: Int,
    val daysLeft: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeView() {


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("dashboard") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                currentScreen = currentScreen,
                onItemSelected = { id ->
                    currentScreen = id
                    scope.launch { drawerState.close() }
                },
                onLogout = { /* Handle Logout */ }
            )
        }
    ) {
        // Switch content based on ID
        when (currentScreen) {

            "dashboard" -> Dashboard(
                onMenuClick = { scope.launch { drawerState.open() } }
            )

            "requests" -> RequestsView(
                onMenuClick = { scope.launch { drawerState.open() } },
                onTicketClick = { id -> /* Navigate to Detail, e.g., via NavController */ }
            )

            "stock" -> StockView(
                onMenuClick = { scope.launch { drawerState.open() } },
                onAddStockClick = { /* Navigate to StockManagementScreen */ }
            )

            "products" -> ProductView(
                onMenuClick = { scope.launch { drawerState.open() } }
            )

            "types" -> ProductTypeView(
                onMenuClick = { scope.launch { drawerState.open() } }
            )
            "students" -> StudentsView(
                onMenuClick = { scope.launch { drawerState.open() } },
                onAddStudent = {}
            )

        }
    }
}
    @Composable
    fun Dashboard(onMenuClick: () -> Unit) {
        val scrollState = rememberScrollState()
        val cardsScrollState = rememberScrollState()

        // Mock Data (Replace with API data later)
        val summary = StockSummary(totalItems = 1250, lowStockCount = 15, expiringSoonCount = 8)
        val expiringList = listOf(
            ExpiringItem("Leite Meio Gordo", "Armazém A", 50, 2),
            ExpiringItem("Iogurte Natural", "Frigorífico 2", 30, 4),
            ExpiringItem("Pão de Forma", "Despensa B", 12, 1),
            ExpiringItem("Queijo Fatiado", "Frigorífico 1", 20, 5)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background_Light)
                .verticalScroll(scrollState)
        ) {
            // 1. Header (Standard IPCA Admin Header)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IPCA_Green_Dark)
                    //.windowInsetsPadding(WindowInsets.statusBars) // Safe area
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
                            text = "Dashboard",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Visão Geral de Stocks",
                            color = Color(0xFFA0C4B5), // Light Green text
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 2. Summary Cards Row
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Resumo Diário",
                    color = IPCA_Green_Dark,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(cardsScrollState), // Horizontal scroll for small screens
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardCard(
                        title = "Total em Stock",
                        value = "${summary.totalItems}",
                        icon = Icons.Default.Inventory,
                        iconColor = IPCA_Green_Dark
                    )
                    DashboardCard(
                        title = "Stock Crítico",
                        value = "${summary.lowStockCount}",
                        icon = Icons.Default.Warning,
                        iconColor = Warning_Orange
                    )
                    DashboardCard(
                        title = "A Expirar",
                        value = "${summary.expiringSoonCount}",
                        icon = Icons.Default.WarningAmber,
                        iconColor = Alert_Red
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // 3. Expiring Stock Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Atenção: Validade Próxima",
                        color = IPCA_Green_Dark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { /* Navigate to full list */ }) {
                        Text("Ver Todos", color = IPCA_Gold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Expiring List Table
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column {
                        // Table Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(IPCA_Green_Dark.copy(alpha = 0.05f))
                                .padding(16.dp)
                        ) {
                            Text(
                                "Produto",
                                Modifier.weight(2f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = IPCA_Green_Dark
                            )
                            Text(
                                "Qtd.",
                                Modifier.weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = IPCA_Green_Dark
                            )
                            Text(
                                "Expira em",
                                Modifier.weight(1.5f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = IPCA_Green_Dark
                            )
                        }

                        // Items
                        expiringList.forEachIndexed { index, item ->
                            ExpiringItemRow(item)
                            if (index < expiringList.size - 1) {
                                HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp)) // Bottom spacing
            }
        }
    }


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomePreview(
) {
    HomeView()
}
