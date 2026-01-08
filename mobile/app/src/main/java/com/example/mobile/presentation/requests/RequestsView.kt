package com.example.mobile.presentation.requests

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Black

// Reusing Status Enum from previous answer
 enum class TicketStatus { PENDING, IN_PROGRESS, RESOLVED }
// --- Models ---
data class SupportTicket(
    val id: String,
    val studentName: String,
    val studentNumber: String,
    val category: String, // e.g., "Bolsas", "Alojamento", "Alimentação"
    val subject: String,
    val date: String,
    val status: TicketStatus
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    requestId: String,
    onNavigateBack: () -> Unit,
    onUpdateStatus: (String, TicketStatus, String) -> Unit // ID, New Status, Admin Notes
) {
    // --- Mock Data (Ideally fetched via API using requestId) ---
    val ticket = remember {
        SupportTicket(
            id = requestId,
            studentName = "Ana Silva",
            studentNumber = "2021001",
            category = "Alimentação",
            subject = "Necessidade de Cabaz Alimentar",
            date = "10/05/2025",
            status = TicketStatus.PENDING
        )
    }
    val studentEmail = "a2021001@alunos.ipca.pt"
    val studentPhone = "912 345 678"
    val fullDescription = "Sou estudante deslocada e perdi a minha bolsa de estudo este mês. Necessito de apoio alimentar urgente pois não tenho meios para comprar bens essenciais para esta semana."

    // --- State ---
    var currentStatus by remember { mutableStateOf(ticket.status) }
    var adminNotes by remember { mutableStateOf("") } // Record what product was given
    var statusExpanded by remember { mutableStateOf(false) }

    val statusOptions = TicketStatus.entries.toTypedArray()

    Scaffold(
        containerColor = Background_Light,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Detalhe do Pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("#${ticket.id}", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = IPCA_Green_Dark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = IPCA_Green_Dark
                )
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onUpdateStatus(requestId, currentStatus, adminNotes) },
                    modifier = Modifier
                        .padding(20.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Atualizar Processo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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

            // 1. Student Card
            Text("Estudante", color = IPCA_Green_Dark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(IPCA_Green_Dark.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, null, tint = IPCA_Green_Dark, modifier = Modifier.size(30.dp))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(ticket.studentName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Text_Black)
                        Text("Nº ${ticket.studentNumber}", fontSize = 13.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Contact Info
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Email, null, tint = IPCA_Gold, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(studentEmail, fontSize = 12.sp, color = Text_Black)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Phone, null, tint = IPCA_Gold, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(studentPhone, fontSize = 12.sp, color = Text_Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Request Content
            Text("Pedido de Apoio", color = IPCA_Green_Dark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Category Badge
                    Surface(
                        color = IPCA_Gold,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = ticket.category.uppercase(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Assunto:", fontSize = 12.sp, color = Color.Gray)
                    Text(ticket.subject, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Text_Black)

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Descrição:", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = fullDescription,
                        fontSize = 14.sp,
                        color = Text_Black,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Admin Action Area (Fulfillment)
            Text("Gestão do Processo", color = IPCA_Green_Dark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, IPCA_Gold.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Status Dropdown
                    Text("Estado do Pedido", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    ExposedDropdownMenuBox(
                        expanded = statusExpanded,
                        onExpandedChange = { statusExpanded = !statusExpanded }
                    ) {
                        OutlinedTextField(
                            value = getStatusLabel(currentStatus),
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = IPCA_Green_Dark,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = statusExpanded,
                            onDismissRequest = { statusExpanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            statusOptions.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(getStatusLabel(status)) },
                                    onClick = {
                                        currentStatus = status
                                        statusExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Admin Notes / Product Assignment
                    Text("Produtos Atribuídos / Notas", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = adminNotes,
                        onValueChange = { adminNotes = it },
                        placeholder = { Text("Ex: Entregue Cabaz Alimentar Tipo A; Laptop #402...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IPCA_Green_Dark,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Inventory, null, tint = Color.Gray) // Inventory icon hints at product assignment
                        }
                    )
                    Text(
                        text = "Registe aqui os bens doados que foram entregues ao aluno.",
                        fontSize = 11.sp,
                        color = IPCA_Gold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Space for Bottom Bar
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// Helper to map Enum to Portuguese Text
fun getStatusLabel(status: TicketStatus): String {
    return when (status) {
        TicketStatus.PENDING -> "Pendente"
        TicketStatus.IN_PROGRESS -> "Em Análise / A preparar"
        TicketStatus.RESOLVED -> "Resolvido / Entregue"
    }
}

@Preview
@Composable
fun RequestDetailPreview() {
    RequestDetailScreen(requestId = "T001", onNavigateBack = {}, onUpdateStatus = { _,_,_ -> })
}