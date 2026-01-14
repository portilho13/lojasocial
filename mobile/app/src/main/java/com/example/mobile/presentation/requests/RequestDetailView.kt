package com.example.mobile.presentation.requests

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.presentation.requests.admin.ApiRequest
import com.example.mobile.presentation.requests.admin.ApiRequestItem
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Black
import com.example.mobile.presentation.ui.theme.Warning_Orange
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// --- Reusing Models (Ensure these match your project) ---
// data class ApiRequestItem(...)
// data class ApiRequest(...)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailView(
    requestId: String,
    isAdmin: Boolean = false, // CONTROL FLAG
    onNavigateBack: () -> Unit,
    onAdminUpdateStatus: (String, String) -> Unit = { _, _ -> } // ID, New Status
) {
    // --- Mock Data (Replace with API Fetch) ---
    val request = remember {
        ApiRequest(
            id = "83dc5ece-e885-4e66-8408-78e8018ad408",
            date = "2026-01-08T18:21:31.087Z",
            status = "PENDENTE",
            observation = "Preciso de ajuda com bens essenciais para esta semana.",
            studentId = "f5f101e6",
            items = listOf(
                ApiRequestItem("1", "p1", "Arroz Agulha 1kg", 2, 0, ""),
                ApiRequestItem("2", "p2", "Massa Esparguete", 2, 0, "")
            )
        )
    }
    val studentName = "Ana Silva (2021001)" // Mock lookup

    // --- State ---
    var currentStatus by remember { mutableStateOf(request.status) }
    var statusExpanded by remember { mutableStateOf(false) }

    // Status Options for Admin
    val statusOptions = listOf("PENDENTE", "ENTREGUE", "CANCELADO")

    Scaffold(
        containerColor = Background_Light,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Detalhe do Pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("#${request.id.take(8)}...", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = IPCA_Green_Dark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = IPCA_Green_Dark
                )
            )
        },
        bottomBar = {
            // ADMIN ONLY ACTION BAR
            if (isAdmin) {
                Surface(
                    color = Color.White,
                    shadowElevation = 16.dp, // High elevation to separate from content
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Ações de Administrador", color = IPCA_Gold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            // Status Dropdown
                            ExposedDropdownMenuBox(
                                expanded = statusExpanded,
                                onExpandedChange = { statusExpanded = !statusExpanded },
                                modifier = Modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    value = currentStatus,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Estado") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = IPCA_Green_Dark,
                                        unfocusedBorderColor = Color.LightGray
                                    ),
                                    modifier = Modifier.menuAnchor()
                                )
                                ExposedDropdownMenu(
                                    expanded = statusExpanded,
                                    onDismissRequest = { statusExpanded = false },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    statusOptions.forEach { status ->
                                        DropdownMenuItem(
                                            text = { Text(status) },
                                            onClick = {
                                                currentStatus = status
                                                statusExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Update Button
                            Button(
                                onClick = { onAdminUpdateStatus(requestId, currentStatus) },
                                modifier = Modifier
                                    .height(56.dp) // Match TextField height
                                    .weight(0.6f),
                                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Green_Dark),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text("Gravar")
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            // 1. Status Banner
            StatusBanner(status = currentStatus)
            Spacer(modifier = Modifier.height(20.dp))

            // 2. Student Info (Visible to All)
            Text("Estudante", color = IPCA_Green_Dark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(IPCA_Green_Dark.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, null, tint = IPCA_Green_Dark)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(studentName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Text_Black)
                        Text("ID: ${request.studentId.take(8)}...", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Request Details (Observation)
            Text("Detalhes", color = IPCA_Green_Dark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Observação / Motivo:", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = request.observation,
                        fontSize = 14.sp,
                        color = Text_Black,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Date
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.History, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Criado em: ${formatDate(request.date)}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Requested Items List
            Text("Itens Solicitados", color = IPCA_Green_Dark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

            request.items.forEach { item ->
                ItemRowCard(item)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Extra space at bottom if Admin controls are visible
            if (isAdmin) Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun StatusBanner(status: String) {
    val (color, icon, text) = when (status) {
        "PENDENTE" -> Triple(Warning_Orange, Icons.Default.WarningAmber, "Aguardando Aprovação")
        "ENTREGUE" -> Triple(IPCA_Green_Dark, Icons.Default.CheckCircle, "Pedido Entregue")
        "CANCELADO" -> Triple(Alert_Red, Icons.Default.Block, "Pedido Cancelado")
        else -> Triple(Color.Gray, Icons.Default.History, status)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text, fontWeight = FontWeight.Bold, color = color, fontSize = 16.sp)
                if (status == "PENDENTE") {
                    Text("Os serviços sociais estão a analisar o pedido.", fontSize = 12.sp, color = color.copy(alpha = 0.8f))
                }
            }
        }
    }
}

@Composable
fun ItemRowCard(item: ApiRequestItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Inventory, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(item.productName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Text_Black)
                    if (item.observation.isNotEmpty()) {
                        Text(item.observation, fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }

            // Quantity Box
            Surface(
                color = Background_Light,
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Text(
                    text = "Qtd: ${item.qtyRequested}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Text_Black,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(isoString: String): String {
    return try {
        val instant = Instant.parse(isoString)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm").withZone(ZoneId.systemDefault())
        formatter.format(instant)
    } catch (e: Exception) {
        isoString
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Admin View")
@Composable
fun RequestDetailAdminPreview() {
    RequestDetailView(requestId = "1", isAdmin = true, onNavigateBack = {})
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Student View")
@Composable
fun RequestDetailStudentPreview() {
    RequestDetailView(requestId = "1", isAdmin = false, onNavigateBack = {})
}