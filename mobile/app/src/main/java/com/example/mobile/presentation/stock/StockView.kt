package com.example.mobile.presentation.stock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.R
import com.example.mobile.domain.models.StockDto
import com.example.mobile.presentation.stock.components.StockItemCard
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// --- Models ---
data class StockItem(
    val id: String,
    val productName: String,
    val quantity: Int,
    val unit: String,
    val location: String,
    val expiryDate: String?,
    val daysUntilExpiry: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockView(
    onMenuClick: () -> Unit,
    onAddStockClick: () -> Unit,
    viewModel: StockViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showExpiringOnly by remember { mutableStateOf(false) }

    // Load data on first composition
    LaunchedEffect(Unit) {
        viewModel.getAllStock()
    }

    // Convert StockDto to StockItem for UI
    fun StockDto.toStockItem(): StockItem {
        val daysUntilExpiry = try {
            if (expiryDate != null) {
                // Parse ISO date string using SimpleDateFormat
                val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
                val expiryDateTime = isoFormatter.parse(expiryDate)

                if (expiryDateTime != null) {
                    val diffInMillis = expiryDateTime.time - System.currentTimeMillis()
                    TimeUnit.MILLISECONDS.toDays(diffInMillis).toInt()
                } else {
                    999 // Fallback
                }
            } else {
                999 // No expiry date means not expiring soon
            }
        } catch (e: Exception) {
            0
        }

        return StockItem(
            id = id,
            productName = productName,
            quantity = quantity,
            unit = "un", // Default unit
            location = location,
            expiryDate = expiryDate,
            daysUntilExpiry = daysUntilExpiry
        )
    }

    // Filter Logic
    val filteredList = uiState.stocks
        .map { it.toStockItem() }
        .filter { item ->
            val matchesSearch = item.productName.contains(searchQuery, ignoreCase = true) ||
                    item.location.contains(searchQuery, ignoreCase = true)

            if (showExpiringOnly) {
                matchesSearch && item.daysUntilExpiry <= 7
            } else {
                matchesSearch
            }
        }

    Scaffold(
        containerColor = Background_Light,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddStockClick,
                containerColor = IPCA_Gold,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, "Add Stock") },
                text = { Text("Entrada de Stock") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
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
                            text = "Inventário",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Visão Geral de Stocks",
                            color = Color(0xFFA0C4B5),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
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

            // 3. Loading & Error States
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = IPCA_Green_Dark)
                    }
                }
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = uiState.error ?: "Erro desconhecido",
                                color = Alert_Red,
                                fontSize = 16.sp
                            )
                            Button(
                                onClick = { viewModel.getAllStock() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = IPCA_Green_Dark
                                )
                            ) {
                                Text("Tentar novamente")
                            }
                        }
                    }
                }
                else -> {
                    // 4. Stock List
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 1.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredList) { item ->
                            StockItemCard(item)
                        }

                        if (filteredList.isEmpty() && !uiState.isLoading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Nenhum item encontrado.",
                                        color = Color.Gray,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    uiState.successMessage?.let { message ->
        LaunchedEffect(message) {
            // Optional: Show a Snackbar here
            viewModel.clearMessages()
        }
    }
}