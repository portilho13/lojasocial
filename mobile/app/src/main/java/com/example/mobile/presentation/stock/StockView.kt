package com.example.mobile.presentation.stock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.mobile.presentation.stock.components.StockItemCard
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark


// --- Models ---
data class StockItem(
    val id: String,
    val productName: String,
    val quantity: Int,
    val unit: String, // e.g., "kg", "un"
    val location: String, // e.g., "Armazém A"
    val expiryDate: String,
    val daysUntilExpiry: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockListScreen(
    onMenuClick: () -> Unit,
    onAddStockClick: () -> Unit // Navigate to Create Page
) {
    // --- Mock Data ---
    val initialStock = listOf(
        StockItem("101", "Leite Meio Gordo", 50, "L", "Armazém A", "2024-05-20", 3),
        StockItem("102", "Leite Meio Gordo", 120, "L", "Armazém B", "2025-01-10", 200),
        StockItem("103", "Iogurte Natural", 30, "un", "Frigorífico 2", "2024-05-18", 1),
        StockItem("104", "Arroz Agulha", 200, "kg", "Despensa Seca", "2025-12-01", 500),
        StockItem("105", "Peito de Frango", 15, "kg", "Congelador 1", "2024-06-01", 14)
    )

    // --- State ---
    var stockList by remember { mutableStateOf(initialStock) }
    var searchQuery by remember { mutableStateOf("") }
    var showExpiringOnly by remember { mutableStateOf(false) }

    // Filter Logic
    val filteredList = stockList.filter { item ->
        val matchesSearch = item.productName.contains(searchQuery, ignoreCase = true) ||
                item.location.contains(searchQuery, ignoreCase = true)

        if (showExpiringOnly) {
            matchesSearch && item.daysUntilExpiry <= 7 // Filter for items expiring in a week
        } else {
            matchesSearch
        }
    }

    Scaffold(
        containerColor = Background_Light,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddStockClick,
                containerColor = IPCA_Green_Dark,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, "Add Stock") },
                text = { Text("Entrada de Stock") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IPCA_Green_Dark)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(top = 16.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
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
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Gestão de Inventário",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Controlo de Lotes e Validades",
                            color = Color(0xFFA0C4B5),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 2. Filters & Search
            Column(modifier = Modifier.padding(20.dp)) {
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Pesquisar produto ou local...", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = IPCA_Green_Dark) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = IPCA_Green_Dark,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Filter Chip (Expiring Soon)
                FilterChip(
                    selected = showExpiringOnly,
                    onClick = { showExpiringOnly = !showExpiringOnly },
                    label = { Text("A expirar em 7 dias") },
                    leadingIcon = {
                        if (showExpiringOnly) {
                            Icon(Icons.Default.FilterList, null, modifier = Modifier.size(16.dp))
                        }
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Alert_Red.copy(alpha = 0.1f),
                        selectedLabelColor = Alert_Red,
                        selectedLeadingIconColor = Alert_Red
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (showExpiringOnly) Alert_Red else Color.Gray,
                        selectedBorderColor = Alert_Red,
                        enabled = true,
                        selected = showExpiringOnly
                    )
                )
            }

            // 3. Stock List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 1.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredList) { item ->
                    StockItemCard(item)
                }

                if (filteredList.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                            Text("Nenhum item encontrado.", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}



@Preview
@Composable
fun StockListPreview() {
    StockListScreen(onMenuClick = {}, onAddStockClick = {})
}